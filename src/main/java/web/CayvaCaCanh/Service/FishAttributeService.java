package web.CayvaCaCanh.Service;

import web.CayvaCaCanh.model.FishAttributes;

import java.util.List;

public interface FishAttributeService {

    List<FishAttributes> findAll();

    List<FishAttributes> getAllAttributes();
    FishAttributes getAttributesById(Long id);
    void saveAttributes(FishAttributes fishAttributes);
    void deleteAttributesById(Long id);
}
