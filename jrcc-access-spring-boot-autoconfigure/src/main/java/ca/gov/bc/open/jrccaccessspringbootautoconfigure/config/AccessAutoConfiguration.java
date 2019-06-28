package ca.gov.bc.open.jrccaccessspringbootautoconfigure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class AccessAutoConfiguration {

	/**
	 * Configure the JedisConnectionFactory
	 * @param properties The redis properties
	 * @return a JedisConnectionFactory
	 */
	@Bean
	@ConditionalOnMissingBean(JedisConnectionFactory.class)
	public JedisConnectionFactory jedisConnectionFactory(RedisProperties properties) {
		
		switch (properties.getMode()) {
		case CLUSTER:
			RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(properties.getClusterHostAndPort());
			return new JedisConnectionFactory(redisClusterConfiguration);
		default:
			RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(properties.getHost(), properties.getPort());
			return new JedisConnectionFactory(redisStandaloneConfiguration);
		}
	}
	
	/**
	 * Configure the default StringRedisTempate
	 * @param jedisConnectionFactory
	 * @return a StringRedisTemplate
	 */
	@Bean
	@ConditionalOnMissingBean(StringRedisTemplate.class)
	public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {	
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(jedisConnectionFactory);
		return template;
	}
	
}
