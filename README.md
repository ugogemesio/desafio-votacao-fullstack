VotoRedisPerformanceTest
all > com.dbserver.ugo.votacao.voto > VotoRedisPerformanceTest
1
tests

0
failures

0
ignored

16.201s
duration

100%
successful

Tests
Standard output
Tests
Test	Duration	Result
stressTestVotosComRedis()	16.201s	passed
Standard output
Standard Commons Logging discovery in action with spring-jcl: please remove commons-logging.jar from classpath in order to avoid potential conflicts
20:57:23.873 [Test worker] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [com.dbserver.ugo.votacao.voto.VotoRedisPerformanceTest]: VotoRedisPerformanceTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
20:57:24.038 [Test worker] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper -- Found @SpringBootConfiguration com.dbserver.ugo.votacao.VotacaoApplication for test class com.dbserver.ugo.votacao.voto.VotoRedisPerformanceTest

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v3.5.7)

2025-11-19T20:57:24.730-03:00  INFO 374541 --- [    Test worker] c.d.u.v.voto.VotoRedisPerformanceTest    : Starting VotoRedisPerformanceTest using Java 17.0.16 with PID 374541 (started by ugogemesio in /home/ugogemesio/desafio-full-stack/desafio-votacao-fullstack/backend)
2025-11-19T20:57:24.732-03:00  INFO 374541 --- [    Test worker] c.d.u.v.voto.VotoRedisPerformanceTest    : The following 1 profile is active: "test"
2025-11-19T20:57:25.587-03:00  INFO 374541 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2025-11-19T20:57:25.589-03:00  INFO 374541 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2025-11-19T20:57:25.768-03:00  INFO 374541 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 167 ms. Found 4 JPA repository interfaces.
2025-11-19T20:57:25.793-03:00  INFO 374541 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode
2025-11-19T20:57:25.795-03:00  INFO 374541 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data Redis repositories in DEFAULT mode.
2025-11-19T20:57:25.814-03:00  INFO 374541 --- [    Test worker] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.dbserver.ugo.votacao.associado.AssociadoRepository; If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository
2025-11-19T20:57:25.816-03:00  INFO 374541 --- [    Test worker] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.dbserver.ugo.votacao.pauta.PautaRepository; If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository
2025-11-19T20:57:25.816-03:00  INFO 374541 --- [    Test worker] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.dbserver.ugo.votacao.sessao.SessaoRepository; If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository
2025-11-19T20:57:25.818-03:00  INFO 374541 --- [    Test worker] .RepositoryConfigurationExtensionSupport : Spring Data Redis - Could not safely identify store assignment for repository candidate interface com.dbserver.ugo.votacao.voto.VotoRepository; If you want this repository to be a Redis repository, consider annotating your entities with one of these annotations: org.springframework.data.redis.core.RedisHash (preferred), or consider extending one of the following types with your repository: org.springframework.data.keyvalue.repository.KeyValueRepository
2025-11-19T20:57:25.818-03:00  INFO 374541 --- [    Test worker] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 11 ms. Found 0 Redis repository interfaces.
2025-11-19T20:57:26.420-03:00  INFO 374541 --- [    Test worker] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2025-11-19T20:57:26.477-03:00  INFO 374541 --- [    Test worker] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.6.33.Final
2025-11-19T20:57:26.532-03:00  INFO 374541 --- [    Test worker] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2025-11-19T20:57:26.652-03:00  INFO 374541 --- [    Test worker] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2025-11-19T20:57:26.879-03:00  INFO 374541 --- [    Test worker] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:testdb user=SA
2025-11-19T20:57:26.881-03:00  INFO 374541 --- [    Test worker] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2025-11-19T20:57:26.967-03:00  INFO 374541 --- [    Test worker] org.hibernate.orm.connections.pooling    : HHH10001005: Database info:
	Database JDBC URL [Connecting through datasource 'HikariDataSource (HikariPool-1)']
	Database driver: undefined/unknown
	Database version: 2.3.232
	Autocommit mode: undefined/unknown
	Isolation level: undefined/unknown
	Minimum pool size: undefined/unknown
	Maximum pool size: undefined/unknown
2025-11-19T20:57:27.218-03:00  INFO 374541 --- [    Test worker] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2025-11-19T20:57:28.343-03:00  INFO 374541 --- [    Test worker] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2025-11-19T20:57:28.417-03:00  INFO 374541 --- [    Test worker] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2025-11-19T20:57:28.843-03:00  INFO 374541 --- [    Test worker] o.s.d.j.r.query.QueryEnhancerFactory     : Hibernate is in classpath; If applicable, HQL parser will be used.
2025-11-19T20:57:29.832-03:00  WARN 374541 --- [    Test worker] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2025-11-19T20:57:30.855-03:00  INFO 374541 --- [    Test worker] c.d.u.v.voto.VotoRedisPerformanceTest    : Started VotoRedisPerformanceTest in 6.538 seconds (process running for 8.171)
=== RESULTADOS DO TESTE ===
Total de associados: 100000
Votos processados: 100000
Votos duplicados bloqueados: 0
Tempo total: 10768 ms
Votos por segundo: 9286
2025-11-19T20:57:47.081-03:00  INFO 374541 --- [    Test worker] redis.embedded.AbstractRedisInstance     : Stopping redis server...
2025-11-19T20:57:47.094-03:00  INFO 374541 --- [    Test worker] redis.embedded.AbstractRedisInstance     : Redis exited

Wrap lines 
Generated by Gradle 8.14 at 19 de nov. de 2025 20:57:47
