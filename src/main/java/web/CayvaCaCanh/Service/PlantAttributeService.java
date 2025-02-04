package web.CayvaCaCanh.Service;

import web.CayvaCaCanh.model.PlantAttributes;

import java.util.List;

public interface PlantAttributeService {
    List<PlantAttributes> findAll();
    PlantAttributes saveAttributes(PlantAttributes plantAttributes);
    PlantAttributes getAttributesById(Long id);
}
