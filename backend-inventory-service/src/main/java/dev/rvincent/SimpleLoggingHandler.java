package dev.rvincent;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class SimpleLoggingHandler implements ObservationHandler<Observation.Context> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLoggingHandler.class);

    @Override
    public void onStop(Observation.Context context) {
        LOGGER.info("Retrieved item: {} by user: {} : context: {}",
                getItemNameFromContext(context), getUserIdFromContext(context), context.getName());
    }

    @Override
    public void onError(Observation.Context context) {
        LOGGER.info("Invalid item: {} by user: {} : context: {}",
                getItemNameFromContext(context), getUserIdFromContext(context), context.getName());
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }

    private String getUserIdFromContext(Observation.Context context) {
        return StreamSupport.stream(context.getHighCardinalityKeyValues().spliterator(), false)
                .filter(keyValue -> "userId".equals(keyValue.getKey()))
                .map(KeyValue::getValue)
                .findFirst()
                .orElse("unknown user");
    }

    private String getItemNameFromContext(Observation.Context context) {
        return StreamSupport.stream(context.getLowCardinalityKeyValues().spliterator(), false)
                .filter(keyValue -> "itemName".equals(keyValue.getKey()))
                .map(KeyValue::getValue)
                .findFirst()
                .orElse("unknown item");
    }
}
