package hu.tomi.logistic.specification;

import hu.tomi.logistic.model.Address;
import hu.tomi.logistic.model.Address_;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AddressSpecification {

    public static Specification<Address> search(Address address) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (address.getCountryCode() != null && !address.getCountryCode().isEmpty()) {
                predicates.add(cb.equal(root.get(Address_.countryCode), address.getCountryCode()));
            }
            if (address.getZipCode() != null && !address.getZipCode().isEmpty()) {
                predicates.add(cb.equal(root.get(Address_.zipCode), address.getZipCode()));
            }
            if (address.getCity() != null && !address.getCity().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get(Address_.city)), address.getCity().toLowerCase() + "%"));
            }
            if (address.getStreet() != null && !address.getStreet().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get(Address_.street)), address.getStreet().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
