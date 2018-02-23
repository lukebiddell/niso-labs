nsat_vs_mutationrate_3col80 <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab2/wcnf/3col80_5_6.shuffled.cnf.wcnf/runtime_vs_mutationrate_230115.tsv")
nsat_vs_populationsize_3col80 <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab2/wcnf/3col80_5_6.shuffled.cnf.wcnf/runtime_vs_populationsize_230115.tsv")
nsat_vs_tournamentsize_3col80 <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab2/wcnf/3col80_5_6.shuffled.cnf.wcnf/runtime_vs_tournamentsize_230115.tsv")

nsat_vs_mutationrate_SAT11 <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab2/wcnf/SAT11__application__fuhs__AProVE11__AProVE11-09.cnf.wcnf.10.wcnf/runtime_vs_mutationrate_230115.tsv")
nsat_vs_populationsize_SAT11 <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab2/wcnf/SAT11__application__fuhs__AProVE11__AProVE11-09.cnf.wcnf.10.wcnf/runtime_vs_populationsize_230115.tsv")
nsat_vs_tournamentsize_SAT11 <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab2/wcnf/SAT11__application__fuhs__AProVE11__AProVE11-09.cnf.wcnf.10.wcnf/runtime_vs_tournamentsize_230115.tsv")

nsat_vs_mutationrate_teams24 <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab2/wcnf/teams24_l4a.cnf.wcnf/runtime_vs_mutationrate_230115.tsv")
nsat_vs_populationsize_teams24 <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab2/wcnf/teams24_l4a.cnf.wcnf/runtime_vs_populationsize_230115.tsv")
nsat_vs_tournamentsize_teams24 <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab2/wcnf/teams24_l4a.cnf.wcnf/runtime_vs_tournamentsize_230115.tsv")

#3col80
boxplot(nsat~chi, data=nsat_vs_mutationrate_3col80, main="Satisfied Clauses vs Mutation Rate", xlab="Mutation Rate", ylab="Satisfied Clauses")
dev.print(device = png, filename = 'nsat_vs_mutationrate_3col80.png', width = 600, height = 600)

boxplot(nsat~lambda, data=nsat_vs_populationsize_3col80, main="Satisfied Clauses vs Population Size", xlab="Population Size", ylab="Satisfied Clauses")
dev.print(device = png, filename = 'nsat_vs_populationsize_3col80.png', width = 600, height = 600)

boxplot(nsat~k, data=nsat_vs_tournamentsize_3col80, main="Satisfied Clauses vs Tournament Size", xlab="Tournament Size", ylab="Satisfied Clauses")
dev.print(device = png, filename = 'nsat_vs_tournamentsize_3col80.png', width = 600, height = 600)

#SAT11
boxplot(nsat~chi, data=nsat_vs_mutationrate_SAT11, main="Satisfied Clauses vs Mutation Rate", xlab="Mutation Rate", ylab="Satisfied Clauses")
boxplot(nsat~lambda, data=nsat_vs_populationsize_SAT11, main="Satisfied Clauses vs Population Size", xlab="Population Size", ylab="Satisfied Clauses")
boxplot(nsat~k, data=nsat_vs_tournamentsize_SAT11, main="Satisfied Clauses vs Tournament Size", xlab="Tournament Size", ylab="Satisfied Clauses")

#teams24
boxplot(nsat~chi, data=nsat_vs_mutationrate_teams24, main="Satisfied Clauses vs Mutation Rate", xlab="Mutation Rate", ylab="Satisfied Clauses")
boxplot(nsat~lambda, data=nsat_vs_populationsize_teams24, main="Satisfied Clauses vs Population Size", xlab="Population Size", ylab="Satisfied Clauses")
boxplot(nsat~k, data=nsat_vs_tournamentsize_teams24, main="Satisfied Clauses vs Tournament Size", xlab="Tournament Size", ylab="Satisfied Clauses")
