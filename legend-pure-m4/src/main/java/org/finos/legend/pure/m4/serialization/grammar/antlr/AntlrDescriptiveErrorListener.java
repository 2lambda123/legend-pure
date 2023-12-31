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

package org.finos.legend.pure.m4.serialization.grammar.antlr;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.finos.legend.pure.m4.coreinstance.SourceInformation;

public class AntlrDescriptiveErrorListener extends BaseErrorListener
{
    AntlrSourceInformation sourceInformation;

    public AntlrDescriptiveErrorListener(AntlrSourceInformation sourceInformation)
    {
        this.sourceInformation = sourceInformation;
    }

    @Override
    public void syntaxError(
            Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line,
            int charPositionInLine,
            String msg,
            RecognitionException e)
    {
        if (e == null || e.getOffendingToken() == null)
        {
            SourceInformation sourceInformation = this.sourceInformation.getSourceInformationForUnknownErrorPosition(line, charPositionInLine);
            throw new PureParserException(sourceInformation, msg, e);
        }
        else
        {
            org.antlr.v4.runtime.Token offendingToken = e.getOffendingToken();
            SourceInformation sourceInformation = this.sourceInformation.getSourceInformationForOffendingToken(line, charPositionInLine, offendingToken);
            throw new PureParserException(sourceInformation, msg, e);
        }
    }
}
