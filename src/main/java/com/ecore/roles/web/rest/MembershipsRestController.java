package com.ecore.roles.web.rest;

import com.ecore.roles.model.Membership;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.web.MembershipsApi;
import com.ecore.roles.web.dto.MembershipDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ecore.roles.web.dto.MembershipDto.fromModel;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles/memberships")
@Tag(name = "Membership", description = "Manager membership")
public class MembershipsRestController implements MembershipsApi {

    private final MembershipsService membershipsService;

    // I changed it to a PutMapping because this endpoint is updating an existing object
    @Override
    @PutMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    @Operation(summary = "Assign Role To Membership")
    public ResponseEntity<MembershipDto> assignRoleToMembership(
            @NotNull @Valid @RequestBody MembershipDto membershipDto) {
        Membership membership = membershipsService.assignRoleToMembership(membershipDto.toModel());
        return ResponseEntity
                .status(200)
                .body(fromModel(membership));
    }

    // I changed it to a GetMapping because it is looking for information from the backend
    @Override
    @GetMapping(
            path = "/search",
            produces = {"application/json"})
    @Operation(summary = "Get membership by role ID Role To Membership")
    public ResponseEntity<List<MembershipDto>> getMemberships(
            @RequestParam UUID roleId) {

        List<Membership> memberships = membershipsService.getMemberships(roleId);

        List<MembershipDto> newMembershipDto = new ArrayList<>();

        for (Membership membership : memberships) {
            MembershipDto membershipDto = fromModel(membership);
            newMembershipDto.add(membershipDto);
        }

        return ResponseEntity
                .status(200)
                .body(newMembershipDto);
    }

}
