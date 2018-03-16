algorithmtype <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520594541684algorithmtype.tsv")
h <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520594541684h.tsv")
k <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520594541684k.tsv")
lambda <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520594541684lambda.tsv")
mutationrate <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520594541684mutationrate.tsv")

algorithmtype <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520615718148algorithmtype.tsv")
h <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520615718148h.tsv")
k <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520615718148k.tsv")
lambda <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520615718148lambda.tsv")
mutationrate <- read.delim("~/Documents/eclipse-workspace/niso-labs/logs/lab3/1520615718148mutationrate.tsv")

boxplot(b~type, data=algorithmtype, main="Average Number of Individuals in Bar vs Algorithm Type", xlab="Algorithm Type", ylab="Average Number of Individuals in Bar")
dev.print(device = png, filename = 'algorithmtype3.png', width = 600, height = 600)

boxplot(b~h, data=h, main="Average Number of Individuals in Bar vs Number of States per Strategy", xlab="Number of States per Strategy", ylab="Average Number of Individuals in Bar")
dev.print(device = png, filename = 'h3.png', width = 600, height = 600)

boxplot(b~k, data=k, main="Average Number of Individuals in Bar vs Tournament Size", xlab="Tournament Size", ylab="Average Number of Individuals in Bar")
dev.print(device = png, filename = 'k3.png', width = 600, height = 600)

boxplot(b~lambda, data=lambda, main="Average Number of Individuals in Bar vs Population Size", xlab="Population Size", ylab="Average Number of Individuals in Bar")
dev.print(device = png, filename = 'lambda3.png', width = 600, height = 600)

boxplot((b/lambda)~lambda, data=lambda, main="Average Percentage of Individuals in Bar vs Population Size", xlab="Population Size", ylab="Average Percentage of Individuals in Bar")
dev.print(device = png, filename = 'lambdapercent3.png', width = 600, height = 600)

boxplot(b~chi, data=mutationrate, main="Average Number of Individuals in Bar vs Mutation Rate", xlab="Mutation Rate", ylab="Average Number of Individuals in Bar")
dev.print(device = png, filename = 'mutationrate3.png', width = 600, height = 600)
