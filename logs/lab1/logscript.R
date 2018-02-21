#Runtime vs Mutation Rate
boxplot(t*lambda~chi, data=runtime_vs_mutationrate, main="Runtime vs Mutation Rate", xlab="Mutation Rate", ylab="Runtime")
boxplot(t*lambda~chi, data=runtime_vs_mutationrate, main="Runtime vs Mutation Rate", xlab="Mutation Rate", ylab="Runtime", ylim=c(2000, 10000))

#Runtime vs Population Size
boxplot(t*lambda~lambda, data=runtime_vs_populationsize, main="Runtime vs Population Size", xlab="Population Size", ylab="Runtime")
boxplot(t*lambda~lambda, data=runtime_vs_populationsize, main="Runtime vs Population Size", xlab="Population Size", ylab="Runtime", xlim=c(0,15), ylim=c(0,8000))

#Runtime vs Problem Size
boxplot(t*lambda~n, data=runtime_vs_problemsize, main="Runtime vs Problem Size", xlab="Problem Size", ylab="Runtime")
boxplot(t*lambda~n, data=runtime_vs_problemsize, main="Runtime vs Problem Size", xlab="Problem Size", ylab="Runtime", xlim=c(0,15), ylim=c(0,1500))

#Runtime vs Tournament Size
boxplot(t*lambda~k, data=runtime_vs_tournamentsize, main="Runtime vs Tournament Size", xlab="Tournament Size", ylab="Runtime")
