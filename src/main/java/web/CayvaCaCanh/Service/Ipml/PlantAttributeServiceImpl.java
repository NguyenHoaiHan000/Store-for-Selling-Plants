package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.PlantAttributeService;
import web.CayvaCaCanh.model.PlantAttributes;
import web.CayvaCaCanh.repository.PlantAttributesRepository;

import java.util.List;
@Service
public class PlantAttributeServiceImpl implements PlantAttributeService {

    @Autowired
    private PlantAttributesRepository plantAttributesRepository;

    @Override
    public List<PlantAttributes> findAll() {
        return plantAttributesRepository.findAll();
    }

    @Override
    public PlantAttributes saveAttributes(PlantAttributes plantAttributes) {
        return plantAttributesRepository.save(plantAttributes);
    }



    @Override
    public PlantAttributes getAttributesById(Long id) {
        return plantAttributesRepository.findById(id).orElse(null);
    }

}
