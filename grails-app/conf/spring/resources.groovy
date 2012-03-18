import git.mirror.*
import org.springframework.scheduling.timer.ScheduledTimerTask
import org.springframework.scheduling.timer.TimerFactoryBean

// Place your Spring DSL code here
beans = {
	nameLocationResolver(NameLocationResolver)
	usernameLocationResolver(UsernameLocationResolver)
	serviceLocationResolver(ServiceLocationResolver)
}
