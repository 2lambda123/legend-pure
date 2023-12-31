// Copyright 2020 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.pure.runtime.java.compiled.compiler;

import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.MutableSet;

import java.io.IOException;
import java.nio.file.Path;

public class MemoryClassLoader extends ClassLoader
{
    private final MemoryFileManager manager;
    private final MutableSet<String> loadedClassNames = Sets.mutable.empty();

    public MemoryClassLoader(MemoryFileManager manager, ClassLoader cl)
    {
       super(cl);
       this.manager = manager;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException
    {
        synchronized (this.loadedClassNames)
        {
            if (!this.loadedClassNames.contains(name))
            {
                ClassJavaSource mc = this.manager.getClassJavaSourceByName(name);
                if (mc != null)
                {
                    this.loadedClassNames.add(name);
                    byte[] array = mc.getBytes();
                    return defineClass(name, array, 0, array.length);
                }
            }
        }
        return super.findClass(name);
    }

    public int loadedClassCount()
    {
        return this.loadedClassNames.size();
    }

    public void loadClassesFromJarFile(Path jar) throws IOException
    {
        this.manager.loadClassesFromJarFile(jar);
    }
}