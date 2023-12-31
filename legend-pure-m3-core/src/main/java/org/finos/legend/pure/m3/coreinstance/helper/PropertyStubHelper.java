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

package org.finos.legend.pure.m3.coreinstance.helper;

import org.eclipse.collections.api.block.function.Function;
import org.finos.legend.pure.m3.coreinstance.meta.pure.metamodel._import.PropertyStub;
import org.finos.legend.pure.m4.coreinstance.CoreInstance;
import org.finos.legend.pure.m4.exception.PureCompilationException;

public class PropertyStubHelper
{
    public static final Function<CoreInstance, CoreInstance> FROM_STUB_FN = PropertyStubHelper::fromPropertyStub;

    public static CoreInstance fromPropertyStub(CoreInstance instance)
    {
        return (instance instanceof PropertyStub) ? fromPropertyStub((PropertyStub) instance) : instance;
    }

    public static CoreInstance fromPropertyStub(PropertyStub propertyStub)
    {
        if (propertyStub._resolvedPropertyCoreInstance() == null)
        {
            throw new PureCompilationException("Error, PropertyStub needs to be resolved before it can be accessed");
        }
        return propertyStub._resolvedPropertyCoreInstance();
    }

    public static boolean isUnresolved(PropertyStub propertyStub)
    {
        return propertyStub._resolvedProperty() == null;
    }
}