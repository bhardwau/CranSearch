echo "Compiling"

mvn clean install
echo "Exectuing Search Engine"
mvn exec:java -Dexec.mainClass="com.utk.ir.tcd.SearchEngine"

echo "Evaluating Results"
trec_eval/trec_eval trec_eval/QRelsCorrectedforTRECeval ../Docs/results