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

package org.finos.legend.pure.m3.serialization.runtime.cache;

import org.finos.legend.pure.m4.serialization.Reader;
import org.finos.legend.pure.m4.serialization.binary.BinaryReaders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedMemoryPureGraphCache extends MemoryPureGraphCache
{
    public CompressedMemoryPureGraphCache()
    {
    }

    @Override
    protected OutputStream newOutputStream(ByteArrayOutputStream stream)
    {
        try
        {
            return new GZIPOutputStream(stream);
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    protected Reader newReader(byte[] bytes)
    {
        try
        {
            return BinaryReaders.newBinaryReader(new GZIPInputStream(new ByteArrayInputStream(bytes)));
        }
        catch (IOException e)
        {
            throw new UncheckedIOException(e);
        }
    }
}
