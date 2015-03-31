package com.dllx.security.action.admin;

import com.dllx.security.bean.Resource;
import com.dllx.security.service.admin.ResourceService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;

/**
 * 后台Action类 - 资源
 */
@Controller
@ParentPackage("admin")
public class ResourceAction extends BaseAdminAction {

    private static final long serialVersionUID = -1066168819528324882L;

    private Resource resource;

    @javax.annotation.Resource
    private ResourceService resourceService;

    /**
     * 是否已存在ajax验证
     *
     * @return json
     */
    public String checkName() {
        if (resourceService.isExistName(resource.getName())) {
            return ajaxText("true");
        } else {
            return ajaxText("false");
        }
    }

    /**
     * 是否已存在ajax验证
     *
     * @return json
     */
    public String checkValue() {
        if (resourceService.isExistValue(resource.getValue())) {
            return ajaxText("true");
        } else {
            return ajaxText("false");
        }
    }

    // 列表
    public String list() {
        pager = resourceService.findByPager(pager);
        return LIST;
    }

    // 删除
    public String delete() throws Exception {
        resourceService.deleteResource(ids);
        return ajaxJsonSuccessMessage("删除成功！");
    }

    // 添加
    public String add() {
        return INPUT;
    }

    // 编辑
    public String edit() {
        resource = resourceService.getResource(id);
        return INPUT;
    }

    // 保存
    @Validations(
            requiredStrings = {
                    @RequiredStringValidator(fieldName = "resource.name", message = "资源名称不允许为空!"),
                    @RequiredStringValidator(fieldName = "resource.value", message = "资源值不允许为空!")
            }
    )
    @InputConfig(resultName = "error")
    public String save() throws Exception {
        resource.setRoleList(null);
        resourceService.saveResource(resource);
        redirectionUrl = "resource!list.action";
        return SUCCESS;
    }

    // 更新
    @Validations(
            requiredStrings = {
                    @RequiredStringValidator(fieldName = "resource.name", message = "资源名称不允许为空!"),
                    @RequiredStringValidator(fieldName = "resource.value", message = "资源值不允许为空!")
            }
    )
    @InputConfig(resultName = "error")
    public String update() throws Exception {
        Resource persistent = resourceService.getResource(id);
        if (persistent.getIsSystem()) {
            addActionError("系统内置资源不允许修改!");
            return ERROR;
        }
        BeanUtils.copyProperties(resource, persistent, new String[]{"pkId", "createDate", "modifyDate", "isSystem", "roleList"});
        resourceService.updateResource(persistent);
        redirectionUrl = "resource!list.action";
        return SUCCESS;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}