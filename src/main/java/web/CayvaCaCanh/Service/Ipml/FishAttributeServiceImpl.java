package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.FishAttributeService;
import web.CayvaCaCanh.model.FishAttributes;
import web.CayvaCaCanh.repository.FishAttributesRepository;

import java.util.List;
@Service
public class FishAttributeServiceImpl implements FishAttributeService {
    @Autowired
    private FishAttributesRepository fishAttributesRepository;

    @Override
    public List<FishAttributes> findAll() {
        return fishAttributesRepository.findAll();
    }

    @Override
    public List<FishAttributes> getAllAttributes() {
        return fishAttributesRepository.findAll();
    }

    @Override
    public FishAttributes getAttributesById(Long id) {
        return fishAttributesRepository.findById(id).orElse(null);
    }

    @Override
    public void saveAttributes(FishAttributes fishAttributes) {
        fishAttributesRepository.save(fishAttributes);
    }

    @Override
    public void deleteAttributesById(Long id) {
        fishAttributesRepository.deleteById(id);
    }
}
