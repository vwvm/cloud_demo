package zotci.gateway.predicate;

import jakarta.validation.constraints.NotEmpty;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

@Component
public class VIPRoutePredicateFactory extends AbstractRoutePredicateFactory<VIPRoutePredicateFactory.Config> {

    public VIPRoutePredicateFactory() {
        super(Config.class);
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("param", "value");
    }

    /**
     * 这是一个方法重写（Override），它来自于 Spring Cloud Gateway 的 PredicateFactory 接口。
     * PredicateFactory 的作用就是“生产”出一个个用于匹配请求的 Predicate 对象。
     */
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {

        // 方法返回一个新创建的 GatewayPredicate 对象。
        // GatewayPredicate 是一个函数式接口，核心就是 test 方法。
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                // ServerWebExchange 是 Spring WebFlux 中的核心对象，封装了一次 HTTP 请求-响应的完整上下文。
                // 我们可以从中获取请求（request）、响应（response）等所有信息。

                // 1. 从请求上下文中获取 HttpRequest 对象
                ServerHttpRequest request = serverWebExchange.getRequest();

                // 2. 获取请求的所有查询参数，并尝试获取指定名称的第一个参数值。
                //    - request.getQueryParams() 返回一个 MultiValueMap<String, String>，
                //      它可以存储一个 key 对应多个 value 的情况（例如 ?name=张三&name=李四）。
                //    - predicate.param 是我们在配置文件中定义的参数名称（比如 "flag"）。
                //    - .getFirst(predicate.param) 会获取这个参数对应的第一个值。
                String first = request.getQueryParams().getFirst(config.param);

                // 3. 核心判断逻辑：
                //    - StringUtils.hasText(first)：确保获取到的参数值不是 null，也不是空字符串或只包含空格。
                //    - first.equals(predicate.value)：确保参数值与我们在配置文件中预设的值（比如 "internal"）完全相等。
                //    - 两个条件用 && 连接，意味着必须同时满足。
                return StringUtils.hasText(first) && first.equals(config.value);
            }
        };
    }

    /**
     * 配置可用参数
     */
    public static class Config {

        public @NotEmpty String getParam() {
            return param;
        }

        public void setParam(@NotEmpty String param) {
            this.param = param;
        }

        public @NotEmpty String getValue() {
            return value;
        }

        public void setValue(@NotEmpty String value) {
            this.value = value;
        }

        @NotEmpty
        private String param;

        @NotEmpty
        private String value;


    }
}
