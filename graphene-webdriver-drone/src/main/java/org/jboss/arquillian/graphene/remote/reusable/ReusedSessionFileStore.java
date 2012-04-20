/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.graphene.remote.reusable;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jboss.arquillian.graphene.utils.SerializationUtils;

/**
 * Loads and writes {@link ReusedSessionStore} from/to file.
 * 
 * @author Lukas Fryc
 */
public class ReusedSessionFileStore {

    public ReusedSessionStore loadStoreFromFile(File file) {
        byte[] readStore = readStore(file);
        if (readStore == null) {
            return null;
        }
        try {
            ReusedSessionStore loadedSession = (ReusedSessionStore) SerializationUtils.deserializeFromBytes(readStore);
            return loadedSession;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void writeStoreToFile(File file, ReusedSessionStore store) {
        try {
            byte[] serialized = SerializationUtils.serializeToBytes(store);
            writeStore(file, serialized);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }

    private byte[] readStore(File file) {
        if (!file.exists()) {
            return null;
        }
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            // TODO logger
            System.err.println("Problems reading file '" + file + "'");
            return null;
        }
    }

    private boolean writeStore(File file, byte[] data) {
        try {
            FileUtils.writeByteArrayToFile(file, data);
            return true;
        } catch (IOException e) {
            // TODO logger
            System.err.println("Problems writing to storeFile '" + file + "'");
            return false;
        }
    }
}