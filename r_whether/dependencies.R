cranRepo <- 'http://cran.stat.ucla.edu/'
install.packages("devtools", repos=cranRepo)
devtools::install_github("RcppCore/Rcpp")
devtools::install_github("rstats-db/DBI")
devtools::install_github("rstats-db/RPostgres")

install.packages("ggplot2", repos=cranRepo)
install.packages("maps", repos=cranRepo)
