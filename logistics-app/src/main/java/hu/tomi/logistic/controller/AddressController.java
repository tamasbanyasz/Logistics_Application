package hu.tomi.logistic.controller;

import hu.tomi.logistic.dto.AddressDto;
import hu.tomi.logistic.mapper.AddressMapper;
import hu.tomi.logistic.model.Address;
import hu.tomi.logistic.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @GetMapping
    public List<AddressDto> getAllAddresses() {
        return addressService.getAllAddresses().stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AddressDto getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        return addressMapper.toDto(address);
    }

    @PostMapping
    public ResponseEntity<?> createAddress(@Valid @RequestBody AddressDto dto) {
        Address entity = addressMapper.toEntity(dto);
        Address saved = addressService.createAddress(entity);
        AddressDto savedDto = addressMapper.toDto(saved);
        return ResponseEntity.ok(Map.of("id", savedDto.getId()));
    }

    @PutMapping("/{id}")
    public AddressDto updateAddress(@PathVariable Long id, @Valid @RequestBody AddressDto dto) {
        Address entity = addressMapper.toEntity(dto);
        Address updated = addressService.updateAddress(id, entity);
        return addressMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/search")
    public ResponseEntity<List<AddressDto>> searchAddresses(
            @RequestBody AddressDto dto,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort) {

        Address filter = addressMapper.toEntity(dto);

        if (filter == null || allAddressFieldsEmpty(filter)) {
            return ResponseEntity.badRequest().build();
        }

        Pageable pageable = createPageRequest(page, size, sort);
        Page<Address> results = addressService.searchAddresses(filter, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(results.getTotalElements()));

        List<AddressDto> dtos = results.getContent().stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, headers, HttpStatus.OK);
    }

    private boolean allAddressFieldsEmpty(Address address) {
        return Stream.of(
                address.getCountryCode(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet()
        ).allMatch(value -> value == null || value.isEmpty());
    }
    
    private Pageable createPageRequest(Integer page, Integer size, String sortParam) {
        int pageNumber = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : Integer.MAX_VALUE;
        Sort sort = createSort(sortParam);
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private Sort createSort(String sortParam) {
        if (sortParam == null || sortParam.isBlank()) {
            return Sort.by(Sort.Direction.ASC, "id");
        }
        String[] parts = sortParam.split(",");
        String field = parts[0];
        Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        return Sort.by(direction, field);
    }
}
