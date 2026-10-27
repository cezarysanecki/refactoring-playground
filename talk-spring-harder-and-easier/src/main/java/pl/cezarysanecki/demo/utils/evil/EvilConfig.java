package pl.cezarysanecki.demo.utils.evil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import pl.cezarysanecki.demo.app.cloud.CloudUtils;

@Configuration
public class EvilConfig {

    @Bean
    @Conditional(EvilCondition.class)
    EvilService evilService() {
        return new EvilService();
    }

    static class EvilCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return CloudUtils.canRegisterBean();
        }

    }

}
