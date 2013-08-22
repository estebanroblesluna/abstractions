package com.modules.boilerpipe;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.core.api.Message;
import com.core.api.ProcessingException;
import com.core.api.Processor;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLHighlighter;

public class BoilerpipeHTMLProcessor implements Processor
{

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    HTMLDocument document = null;

    if (message.getPayload() instanceof String)
    {
      document = new HTMLDocument((String) message.getPayload());
    }
    else if (message.getPayload() instanceof InputStream)
    {
      try
      {
        String data = IOUtils.toString((InputStream) message.getPayload());
        document = new HTMLDocument(data);

      }
      catch (IOException e)
      {
        throw new ProcessingException(e);
      }
    }

    if (document != null)
    {
      try
      {
        final HTMLHighlighter hh = HTMLHighlighter.newExtractingInstance();

        TextDocument doc = new BoilerpipeSAXInput(document.toInputSource()).getTextDocument();
        ArticleExtractor.INSTANCE.process(doc);
        final InputSource is = document.toInputSource();
        
        String html = hh.process(doc, is);
        message.setPayload(html);
      }
      catch (BoilerpipeProcessingException e)
      {
        throw new ProcessingException(e);
      }
      catch (SAXException e)
      {
        throw new ProcessingException(e);
      }
    }

    return message;
  }
}
