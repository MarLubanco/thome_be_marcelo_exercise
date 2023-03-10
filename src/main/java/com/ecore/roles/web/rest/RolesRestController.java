package com.ecore.roles.web.rest;

import com.ecore.roles.model.Role;
import com.ecore.roles.service.RolesService;
import com.ecore.roles.web.RolesApi;
import com.ecore.roles.web.dto.RoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ecore.roles.web.dto.RoleDto.fromModel;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles")
@Tag(name = "Role", description = "Manager roles")
public class RolesRestController implements RolesApi {

    private final RolesService rolesService;

    @Override
    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    @Operation(summary = "Create a Role")
    public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto role) {
        return ResponseEntity
                .status(201)
                .body(fromModel(rolesService.createRole(role.toModel())));
    }

    @Override
    @PostMapping(
            produces = {"application/json"})
    @Operation(summary = "Get all roles")
    public ResponseEntity<List<RoleDto>> getRoles() {

        List<Role> getRoles = rolesService.getRoles();

        List<RoleDto> roleDtoList = new ArrayList<>();

        for (Role role : getRoles) {
            RoleDto roleDto = fromModel(role);
            roleDtoList.add(roleDto);
        }

        return ResponseEntity
                .status(200)
                .body(roleDtoList);
    }

    // I changed it here to get because it is a data recovery
    @Override
    @GetMapping(
            path = "/{roleId}",
            produces = {"application/json"})
    @Operation(summary = "Get role by UUID")
    public ResponseEntity<RoleDto> getRole(
            @PathVariable UUID roleId) {
        return ResponseEntity
                .status(200)
                .body(fromModel(rolesService.getRole(roleId)));
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = {"application/json"})
    public ResponseEntity<List<Role>> getRoleByFilters(
            @Param(value = "teamMemberId") UUID teamMemberId,
            @Param(value = "teamId") UUID teamId) {
        List<Role> rolesByFilters = rolesService.getRolesByFilters(teamMemberId, teamId);
        return ResponseEntity
                .status(200)
                .body(rolesByFilters);
    }

}
