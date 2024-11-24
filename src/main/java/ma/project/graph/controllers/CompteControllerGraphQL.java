package ma.project.graph.controllers;

import lombok.AllArgsConstructor;
import ma.project.graph.entities.Compte;
import ma.project.graph.entities.Transaction;
import ma.project.graph.entities.TransactionRequest;
import ma.project.graph.entities.TypeTransaction;
import ma.project.graph.repositories.CompteRepository;
import ma.project.graph.repositories.TransactionRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

@Controller
@AllArgsConstructor
public class CompteControllerGraphQL {
    private final CompteRepository compteRepository;
    private final TransactionRepository transactionRepository;

    @QueryMapping
    public List<Compte> allComptes() {
        return compteRepository.findAll();
    }
    @QueryMapping
    public Compte compteById(@Argument Long id) {
        Compte compte = compteRepository.findById(id).orElse(null);
        if(compte ==null ) throw  new RuntimeException(String.format("Compte %s not found", id));
        else return compte;
    }
    @MutationMapping
    public Compte saveCompte(@Argument Compte compte) {
        return compteRepository.save(compte);
    }

    @QueryMapping
    public Map<String,Object> totalSolde() {
        long count = compteRepository.count();
        Float sum = compteRepository.sumSoldes();
        double average = count > 0? sum/count:0;
        return Map.of(
                "count",count,
                "sum",sum,
                "average",average
        );
    }
    @MutationMapping
    public Transaction addTransaction(@Argument TransactionRequest requesttransaction) {
        Compte compte = compteRepository.findById(requesttransaction.getIdCompte()).orElse(null);
        if(compte!=null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateTransaction = null;
            try {
                dateTransaction = dateFormat.parse(requesttransaction.getDateTransaction());
                Transaction transaction = new Transaction();
                transaction.setType(requesttransaction.getType());
                transaction.setAmount(requesttransaction.getAmount());
                transaction.setDateTransaction(dateTransaction);
                transaction.setCompte(compte);
                return transactionRepository.save(transaction);
            } catch (ParseException e) {
                throw new RuntimeException("Invalid date format. Expected format: yyyy-MM-dd");
            }
        }
        throw  new RuntimeException(String.format("Compte %s not found",requesttransaction.getIdCompte() ));
    }
    @QueryMapping
    public List<Transaction> compteTransactions(@Argument Long idCompte) {
        Compte compte = compteRepository.findById(idCompte).orElseThrow(()->new RuntimeException(String.format("Compte %s not found", idCompte)));
        return transactionRepository.findByCompte(compte);

    }
    @QueryMapping
    public Map<String,Object> transactionStas() {
        long count = transactionRepository.count();
        double sumDepots = transactionRepository.sumByType(TypeTransaction.DEPOT);
        double sumRetraits = transactionRepository.sumByType(TypeTransaction.ROTRAIT);
        return Map.of(
                "count" , count,
                "sumDepots" , sumDepots,
                "sumRetraits",sumRetraits

        );
    }
}
