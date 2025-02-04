package web.CayvaCaCanh.Service.Ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.CayvaCaCanh.Service.RoleService;
import web.CayvaCaCanh.model.Roles;
import web.CayvaCaCanh.repository.RolesRepository;
@Service
public class RoleServiceImpl  implements RoleService {
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Roles findById(String id) {
        return rolesRepository.findById(id).orElse(null);
    }
}
