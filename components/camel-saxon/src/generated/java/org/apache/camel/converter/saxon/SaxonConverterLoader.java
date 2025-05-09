/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.converter.saxon;

import javax.annotation.processing.Generated;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.DeferredContextBinding;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.TypeConverterLoaderException;
import org.apache.camel.spi.TypeConverterLoader;
import org.apache.camel.spi.TypeConverterRegistry;
import org.apache.camel.support.SimpleTypeConverter;
import org.apache.camel.support.TypeConverterSupport;
import org.apache.camel.util.DoubleMap;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@Generated("org.apache.camel.maven.packaging.TypeConverterLoaderGeneratorMojo")
@SuppressWarnings("unchecked")
@DeferredContextBinding
public final class SaxonConverterLoader implements TypeConverterLoader, CamelContextAware {

    private CamelContext camelContext;

    public SaxonConverterLoader() {
    }

    @Override
    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public CamelContext getCamelContext() {
        return camelContext;
    }

    @Override
    public void load(TypeConverterRegistry registry) throws TypeConverterLoaderException {
        registerConverters(registry);
        registerFallbackConverters(registry);
    }

    private void registerConverters(TypeConverterRegistry registry) {
        addTypeConverter(registry, javax.xml.transform.dom.DOMSource.class, net.sf.saxon.om.NodeInfo.class, false,
            (type, exchange, value) -> {
                Object answer = org.apache.camel.converter.saxon.SaxonConverter.toDOMSourceFromNodeInfo((net.sf.saxon.om.NodeInfo) value);
                if (false && answer == null) {
                    answer = Void.class;
                }
                return answer;
            });
        addTypeConverter(registry, javax.xml.transform.dom.DOMSource.class, net.sf.saxon.tree.tiny.TinyDocumentImpl.class, false,
            (type, exchange, value) -> {
                Object answer = org.apache.camel.converter.saxon.SaxonConverter.toDOMSourceFromNodeInfo((net.sf.saxon.tree.tiny.TinyDocumentImpl) value);
                if (false && answer == null) {
                    answer = Void.class;
                }
                return answer;
            });
        addTypeConverter(registry, org.w3c.dom.Document.class, net.sf.saxon.om.NodeInfo.class, false,
            (type, exchange, value) -> {
                Object answer = org.apache.camel.converter.saxon.SaxonConverter.toDOMDocument((net.sf.saxon.om.NodeInfo) value);
                if (false && answer == null) {
                    answer = Void.class;
                }
                return answer;
            });
        addTypeConverter(registry, org.w3c.dom.Document.class, net.sf.saxon.tree.tiny.TinyDocumentImpl.class, false,
            (type, exchange, value) -> {
                Object answer = org.apache.camel.converter.saxon.SaxonConverter.toDOMDocument((net.sf.saxon.tree.tiny.TinyDocumentImpl) value);
                if (false && answer == null) {
                    answer = Void.class;
                }
                return answer;
            });
        addTypeConverter(registry, org.w3c.dom.Document.class, net.sf.saxon.tree.tiny.TinyElementImpl.class, false,
            (type, exchange, value) -> {
                Object answer = org.apache.camel.converter.saxon.SaxonConverter.toDOMDocument((net.sf.saxon.tree.tiny.TinyElementImpl) value);
                if (false && answer == null) {
                    answer = Void.class;
                }
                return answer;
            });
        addTypeConverter(registry, org.w3c.dom.Node.class, net.sf.saxon.om.NodeInfo.class, false,
            (type, exchange, value) -> {
                Object answer = org.apache.camel.converter.saxon.SaxonConverter.toDOMNode((net.sf.saxon.om.NodeInfo) value);
                if (false && answer == null) {
                    answer = Void.class;
                }
                return answer;
            });
        addTypeConverter(registry, org.w3c.dom.Node.class, net.sf.saxon.tree.tiny.TinyDocumentImpl.class, false,
            (type, exchange, value) -> {
                Object answer = org.apache.camel.converter.saxon.SaxonConverter.toDOMNode((net.sf.saxon.tree.tiny.TinyDocumentImpl) value);
                if (false && answer == null) {
                    answer = Void.class;
                }
                return answer;
            });
        addTypeConverter(registry, org.w3c.dom.NodeList.class, java.util.List.class, false,
            (type, exchange, value) -> {
                Object answer = org.apache.camel.converter.saxon.SaxonConverter.toDOMNodeList((java.util.List) value);
                if (false && answer == null) {
                    answer = Void.class;
                }
                return answer;
            });
    }

    private static void addTypeConverter(TypeConverterRegistry registry, Class<?> toType, Class<?> fromType, boolean allowNull, SimpleTypeConverter.ConversionMethod method) {
        registry.addTypeConverter(toType, fromType, new SimpleTypeConverter(allowNull, method));
    }

    private void registerFallbackConverters(TypeConverterRegistry registry) {
        addFallbackTypeConverter(registry, false, false, (type, exchange, value) -> org.apache.camel.converter.saxon.SaxonConverter.convertTo(type, exchange, value, registry));
    }

    private static void addFallbackTypeConverter(TypeConverterRegistry registry, boolean allowNull, boolean canPromote, SimpleTypeConverter.ConversionMethod method) {
        registry.addFallbackTypeConverter(new SimpleTypeConverter(allowNull, method), canPromote);
    }
}
