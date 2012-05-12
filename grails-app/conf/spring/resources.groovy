import gittig.*

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

beans = {
	// Repository location resolvers
	nameLocationResolver(NameLocationResolver)
	usernameLocationResolver(UsernameLocationResolver)
	serviceLocationResolver(ServiceLocationResolver)
	
	// git task executor
	taskExecutor(ThreadPoolTaskExecutor) {
		corePoolSize = application.config.app.gitWorkers
		maxPoolSize = application.config.app.gitWorkers
	}
}
