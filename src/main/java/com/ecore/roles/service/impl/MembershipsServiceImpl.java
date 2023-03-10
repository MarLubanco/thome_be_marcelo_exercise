package com.ecore.roles.service.impl;

import com.ecore.roles.exception.InvalidArgumentException;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.MembershipsService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Log4j2
@Service
@AllArgsConstructor
public class MembershipsServiceImpl implements MembershipsService {

    private final MembershipRepository membershipRepository;
    private final RoleRepository roleRepository;

    @Override
    public Membership assignRoleToMembership(@NonNull Membership memberShip) {

        UUID roleId = ofNullable(memberShip.getRole()).map(Role::getId)
                .orElseThrow(() -> new InvalidArgumentException(Role.class));
        log.info(
                "[MEMBERSHIP=SERVICE - ASSIGN ROLE TO MEMBERSHIP] - Start assign role to membership id: {}  role id: {}",
                memberShip.getUserId(), roleId.toString());
        if (membershipRepository.findByUserIdAndTeamId(memberShip.getUserId(), memberShip.getTeamId())
                .isPresent()) {
            log.error(
                    "[MEMBERSHIP=SERVICE - ASSIGN ROLE TO MEMBERSHIP] - Error role binding to existing team");
            throw new ResourceExistsException(Membership.class);
        }

        roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException(Role.class, roleId));

        log.info("[MEMBERSHIP=SERVICE - ASSIGN ROLE TO MEMBERSHIP] - Save membership success");
        return membershipRepository.save(memberShip);
    }

    @Override
    public List<Membership> getMemberships(@NonNull UUID rid) {
        return membershipRepository.findByRoleId(rid);
    }
}
