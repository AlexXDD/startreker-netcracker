package edu.netcracker.backend.service.impl;

import edu.netcracker.backend.controller.exception.RequestException;
import edu.netcracker.backend.dao.PossibleServiceDAO;
import edu.netcracker.backend.dao.ServiceDAO;
import edu.netcracker.backend.dao.SuggestionDAO;
import edu.netcracker.backend.message.request.DiscountDTO;
import edu.netcracker.backend.message.request.DiscountSuggestionDTO;
import edu.netcracker.backend.message.response.SuggestionDTO;
import edu.netcracker.backend.model.PossibleService;
import edu.netcracker.backend.model.ServiceDescr;
import edu.netcracker.backend.model.Suggestion;
import edu.netcracker.backend.model.TicketClass;
import edu.netcracker.backend.service.DiscountService;
import edu.netcracker.backend.service.SuggestionService;
import edu.netcracker.backend.service.TicketClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SuggestionServiceImpl implements SuggestionService {

    private SuggestionDAO suggestionDAO;

    private PossibleServiceDAO possibleServiceDAO;

    private final DiscountService discountService;

    private final ServiceDAO serviceDAO;

    private final TicketClassService ticketClassService;

    @Autowired
    public SuggestionServiceImpl(SuggestionDAO suggestionDAO,
                                 PossibleServiceDAO possibleServiceDAO,
                                 DiscountService discountService,
                                 ServiceDAO serviceDAO,
                                 TicketClassService ticketClassService) {
        this.suggestionDAO = suggestionDAO;
        this.possibleServiceDAO = possibleServiceDAO;
        this.discountService = discountService;
        this.serviceDAO = serviceDAO;
        this.ticketClassService = ticketClassService;
    }

    @Override
    public List<SuggestionDTO> getAllWithClassId(Number id) {
        log.debug("SuggestionServiceImpl.getAllWithClassId(Number id) was invoked");
        List<Suggestion> suggestions = suggestionDAO.findAllWithClassId(id);

        if (suggestions.size() == 0) {
            log.error("No suggestions yet for ticket class with id {}", id);
            throw new RequestException("No suggestions yet", HttpStatus.NOT_FOUND);
        }

        return suggestions.stream()
                          .map(suggestion -> SuggestionDTO.from(suggestion,
                                                                getAttachedPServices(suggestion).stream()
                                                                                                .map(PossibleService::getPServiceId)
                                                                                                .collect(Collectors.toList())))
                          .collect(Collectors.toList());
    }

    @Override
    public SuggestionDTO getById(Number id) {
        log.debug("SuggestionServiceImpl.getById(Number id) was invoked");
        Optional<Suggestion> optSuggestion = suggestionDAO.find(id);

        if (!optSuggestion.isPresent()) {
            log.error("Suggestion with id {} not found", id);
            throw new RequestException("Suggestion with id " + id + " not found", HttpStatus.NOT_FOUND);
        }

        Suggestion suggestion = optSuggestion.get();

        return SuggestionDTO.from(suggestion, toIdList(getAttachedPServices(suggestion)));
    }

    @Override
    public SuggestionDTO createSuggestion(SuggestionDTO suggestionDTO) {
        log.debug("SuggestionServiceImpl.createSuggestion(SuggestionDTO suggestionDTO) was invoked");
        if (suggestionDTO.getPServiceIds()
                         .size() == 0) {
            log.error("Ticket class with id: {} has no services yet", suggestionDTO.getClassId());
            throw new RequestException("No services attached", HttpStatus.BAD_REQUEST);
        }

        Suggestion suggestion = from(suggestionDTO);
        suggestionDAO.save(suggestion);

        suggestionDTO.getPServiceIds()
                     .forEach(item -> {
                         Optional<PossibleService> optPossibleService = possibleServiceDAO.find(item);

                         if (!optPossibleService.isPresent()) {
                             log.error("Possible service with id: {} not found", item);
                             throw new RequestException("Possible service with id " + item + " not found",
                                                        HttpStatus.NOT_FOUND);
                         }

                         suggestionDAO.addPossibleService(suggestion.getSuggestionId(), item);
                     });

        return SuggestionDTO.from(suggestion, suggestionDTO.getPServiceIds());
    }

    @Override
    public SuggestionDTO updateSuggestion(SuggestionDTO suggestionDTO) {
        log.debug("SuggestionServiceImpl.updateSuggestion(SuggestionDTO suggestionDTO) was invoked");
        Optional<Suggestion> optSuggestion = suggestionDAO.find(suggestionDTO.getId());

        if (!optSuggestion.isPresent()) {
            log.error("Suggestion with id: {} not found", suggestionDTO.getId());
            throw new RequestException("Suggestion with id " + suggestionDTO.getId() + " not found",
                                       HttpStatus.NOT_FOUND);
        }

        Suggestion suggestion = from(suggestionDTO);
        suggestionDAO.save(suggestion);

        updateAttachedPServices(suggestion.getSuggestionId(),
                                suggestionDTO.getPServiceIds(),
                                toIdList(getAttachedPServices(optSuggestion.get())));

        log.debug("Suggestion with id: {} is updated", suggestionDTO.getId());

        return SuggestionDTO.from(suggestion, toIdList(getAttachedPServices(suggestion)));
    }

    @Override
    public void deleteSuggestion(Number id) {
        log.debug("SuggestionServiceImpl.deleteSuggestion(Number id) was invoked");
        Optional<Suggestion> optSuggestion = suggestionDAO.find(id);

        if (!optSuggestion.isPresent()) {
            log.error("Suggestion with id: {} not found", id);
            throw new RequestException("Suggestion with id " + id + " not found", HttpStatus.NOT_FOUND);
        }

        Suggestion suggestion = optSuggestion.get();
        toIdList(getAttachedPServices(suggestion)).forEach(i -> suggestionDAO.deletePossibleService(id, i));

        suggestionDAO.delete(suggestion);

        log.debug("Suggestion with id: {} is deleted", id);
    }

    private List<PossibleService> getAttachedPServices(Suggestion suggestion) {
        return possibleServiceDAO.findAllPossibleServicesBySuggestionId(suggestion.getSuggestionId());
    }

    private void updateAttachedPServices(Number suggestionId, List<Long> newServices, List<Long> oldServices) {
        log.debug(
                "SuggestionServiceImpl.updateAttachedPServices(Number suggestionId, List<Long> newServices, List<Long> oldServices) was invoked");
        for (Long id : newServices) {
            if (!oldServices.contains(id)) {
                suggestionDAO.addPossibleService(suggestionId, id);
            }
        }

        for (Long id : oldServices) {
            if (!newServices.contains(id)) {
                suggestionDAO.deletePossibleService(suggestionId, id);
            }
        }
    }

    private Suggestion from(SuggestionDTO suggestionDTO) {
        Suggestion suggestion = new Suggestion();

        suggestion.setSuggestionId(suggestionDTO.getId());
        suggestion.setClassId(suggestionDTO.getClassId());
        suggestion.setDiscountId(suggestionDTO.getDiscountId());

        return suggestion;
    }

    private List<Long> toIdList(List<PossibleService> possibleServices) {
        return possibleServices.stream()
                               .map(PossibleService::getPServiceId)
                               .collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<DiscountSuggestionDTO>> getSuggestionsRelatedToTicketClasses(Map<Long, List<TicketClass>> relatedToTripsTicketClasses) {
        log.debug(
                "SuggestionServiceImpl.getSuggestionsRelatedToTicketClasses(Map<Long, List<TicketClass>> relatedToTripsTicketClasses was invoked");
        log.debug("get all suggestion that belong to ticket classes that relates to trips "
                  + relatedToTripsTicketClasses.keySet());

        Map<Long, List<Suggestion>> relatedSuggestions = getAllSuggestionBelongToTicketClasses(
                relatedToTripsTicketClasses);

        if (relatedSuggestions.isEmpty()) {
            log.warn("No suggestions for ticket classes {}", relatedToTripsTicketClasses);
            return new HashMap<>();
        }

        Map<Long, List<ServiceDescr>> relationServices = getAllServicesBelongToSuggestions(relatedSuggestions);
        List<DiscountDTO> discountsDTO = getDiscountDTOs(relatedSuggestions);

        Map<Long, List<DiscountSuggestionDTO>> relatedToTripSuggestion = new HashMap<>();

        for (Long tripId : relatedToTripsTicketClasses.keySet()) {
            createSuggestionDTOsBelongToTrip(relatedToTripsTicketClasses,
                                             relatedSuggestions,
                                             relationServices,
                                             discountsDTO,
                                             relatedToTripSuggestion,
                                             tripId);
        }

        return relatedToTripSuggestion;
    }

    @Override
    public DiscountSuggestionDTO createDiscountForSuggestion(DiscountSuggestionDTO suggestionDTO, Number userId) {
        log.debug(
                "SuggestionServiceImpl.createDiscountForSuggestion(DiscountSuggestionDTO suggestionDTO, Number userId) was invoked");
        log.debug("Create discount for suggestion " + suggestionDTO.getSuggestionId());

        Suggestion suggestion = getSuggestion(suggestionDTO, userId);
        DiscountDTO discountDTO = discountService.saveDiscount(suggestionDTO.getDiscountDTO());
        suggestion.setDiscountId(discountDTO.getDiscountId());
        suggestionDAO.save(suggestion);

        TicketClass ticketClass = ticketClassService.find(suggestion.getClassId());
        List<ServiceDescr> serviceDescrs = serviceDAO.getAllServicesBelongToSuggestions(Collections.singletonList(
                suggestion.getSuggestionId()))
                                                     .get(suggestion.getSuggestionId());

        return DiscountSuggestionDTO.toDiscountSuggestionDTO(ticketClass,
                                                             suggestion,
                                                             discountDTO,
                                                             serviceDescrs.stream()
                                                                          .map(ServiceDescr::getServiceName)
                                                                          .collect(Collectors.toList()));
    }

    @Override
    public DiscountSuggestionDTO deleteDiscountForSuggestion(Number discountId, Number userId) {
        log.debug("SuggestionServiceImpl.deleteDiscountForSuggestion(Number discountId, Number userId) was invoked");
        Optional<Suggestion> optionalSuggestion = suggestionDAO.getSuggestionByDiscount(userId, discountId);

        if (!optionalSuggestion.isPresent()) {
            log.error("No such discount with id {}", discountId);
            throw new RequestException("No such discount", HttpStatus.NOT_FOUND);
        }

        Suggestion suggestion = optionalSuggestion.get();
        TicketClass ticketClass = ticketClassService.find(suggestion.getClassId());
        List<ServiceDescr> serviceDescrs = serviceDAO.getAllServicesBelongToSuggestions(Collections.singletonList(
                suggestion.getSuggestionId()))
                                                     .get(suggestion.getSuggestionId());

        suggestion.setDiscountId(null);
        suggestionDAO.save(suggestion);

        DiscountDTO discountDTO = discountService.deleteDiscount(discountId);

        return DiscountSuggestionDTO.toDiscountSuggestionDTO(ticketClass,
                                                             suggestion,
                                                             discountDTO,
                                                             serviceDescrs.stream()
                                                                          .map(ServiceDescr::getServiceName)
                                                                          .collect(Collectors.toList()));
    }

    private void createSuggestionDTOsBelongToTrip(Map<Long, List<TicketClass>> relatedToTripsTicketClasses,
                                                  Map<Long, List<Suggestion>> relatedSuggestions,
                                                  Map<Long, List<ServiceDescr>> relationServices,
                                                  List<DiscountDTO> discountsDTO,
                                                  Map<Long, List<DiscountSuggestionDTO>> relatedToTripSuggestion,
                                                  Long tripId) {
        log.debug(
                "SuggestionServiceImpl.createSuggestionDTOsBelongToTrip(Map<Long, List<TicketClass>> relatedToTripsTicketClasses,\n"
                + "                                                  Map<Long, List<Suggestion>> relatedSuggestions,\n"
                + "                                                  Map<Long, List<ServiceDescr>> relationServices,\n"
                + "                                                  List<DiscountDTO> discountsDTO,\n"
                + "                                                  Map<Long, List<DiscountSuggestionDTO>> relatedToTripSuggestion,\n"
                + "                                                  Long tripId) was invoked");
        List<TicketClass> ticketClasses = relatedToTripsTicketClasses.get(tripId);
        List<DiscountSuggestionDTO> allDiscountSuggestionDTOsRelatedToTrip = relatedToTripSuggestion.computeIfAbsent(
                tripId,
                aLong -> new ArrayList<>());

        for (TicketClass ticketClass : ticketClasses) {
            List<Suggestion> suggestions = relatedSuggestions.get(ticketClass.getClassId());

            if (suggestions == null) {
                continue;
            }

            allDiscountSuggestionDTOsRelatedToTrip.addAll(createSuggestionDTOs(ticketClass,
                                                                               suggestions,
                                                                               relationServices,
                                                                               discountsDTO));
        }

        allDiscountSuggestionDTOsRelatedToTrip.sort(Comparator.comparing(discountSuggestionDTO -> discountSuggestionDTO.getSuggestionId()
                                                                                                  * -1));
    }

    private List<DiscountDTO> getDiscountDTOs(Map<Long, List<Suggestion>> relatedSuggestions) {
        log.debug("SuggestionServiceImpl.getDiscountDTOs(Map<Long, List<Suggestion>> relatedSuggestion) was invoked");
        return discountService.getDiscountDTOs(relatedSuggestions.values()
                                                                 .stream()
                                                                 .flatMap(Collection::stream)
                                                                 .map(Suggestion::getDiscountId)
                                                                 .collect(Collectors.toList()));
    }

    private Map<Long, List<ServiceDescr>> getAllServicesBelongToSuggestions(Map<Long, List<Suggestion>> relatedSuggestions) {
        return serviceDAO.getAllServicesBelongToSuggestions(relatedSuggestions.values()
                                                                              .stream()
                                                                              .flatMap(Collection::stream)
                                                                              .map(Suggestion::getSuggestionId)
                                                                              .collect(Collectors.toList()));
    }

    private Map<Long, List<Suggestion>> getAllSuggestionBelongToTicketClasses(Map<Long, List<TicketClass>> relatedToTripsTicketClasses) {
        return suggestionDAO.getAllSuggestionBelongToTicketClasses(relatedToTripsTicketClasses.values()
                                                                                              .stream()
                                                                                              .flatMap(Collection::stream)
                                                                                              .map(TicketClass::getClassId)
                                                                                              .collect(Collectors.toList()));
    }

    private Suggestion getSuggestion(DiscountSuggestionDTO simpleSuggestionDTO, Number userId) {
        log.debug(
                "SuggestionServiceImpl.getSuggestion(DiscountSuggestionDTO simpleSuggestionDTO, Number userId) was invoked");
        Optional<Suggestion> optionalSuggestion
                = suggestionDAO.findSuggestionBelongToCarrier(simpleSuggestionDTO.getSuggestionId(), userId);

        if (!optionalSuggestion.isPresent()) {
            log.error("No such suggestion with id {}", simpleSuggestionDTO.getSuggestionId());
            throw new RequestException("No such suggestion with id " + simpleSuggestionDTO.getSuggestionId(),
                                       HttpStatus.NOT_FOUND);
        }

        Suggestion suggestion = optionalSuggestion.get();

        if (suggestion.getDiscountId() != null) {
            log.error("Discount already exists for suggestion with id{}", suggestion.getSuggestionId());
            throw new RequestException("Discount already exist", HttpStatus.CONFLICT);
        }
        return suggestion;
    }

    private List<DiscountSuggestionDTO> createSuggestionDTOs(TicketClass ticketClass,
                                                             List<Suggestion> suggestions,
                                                             Map<Long, List<ServiceDescr>> relatedToSuggestionServices,
                                                             List<DiscountDTO> discountDTOs) {
        List<DiscountSuggestionDTO> discountSuggestionDTOs = new ArrayList<>();
        for (Suggestion suggestion : suggestions) {
            discountSuggestionDTOs.add(DiscountSuggestionDTO.toDiscountSuggestionDTO(ticketClass,
                                                                                     suggestion,
                                                                                     discountService.getRelatedDiscountDTO(
                                                                                             suggestion.getDiscountId(),
                                                                                             discountDTOs),
                                                                                     relatedToSuggestionServices.get(
                                                                                             suggestion.getSuggestionId())
                                                                                                                .stream()
                                                                                                                .map(ServiceDescr::getServiceName)
                                                                                                                .collect(
                                                                                                                        Collectors.toList())));
        }

        return discountSuggestionDTOs;
    }
}
