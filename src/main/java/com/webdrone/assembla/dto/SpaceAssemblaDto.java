package com.webdrone.assembla.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement(name = "space")
public class SpaceAssemblaDto {

	private String id;

	private String name;

	private String description;

	private String wikiName;

	private int publicPermissions;

	private int teamPermissions;

	private int watcherPermissions;

	private boolean sharePermissions;

	private int teamTabRole;

	private DateTime createdAt;

	private DateTime updatedAt;

	private String defaultShowpage;

	private String tabsOrder;

	private String parentId;

	private boolean restricted;

	private DateTime restrictedDate;

	private DateTime commercialFrom;

	private String banner;

	private BigDecimal bannerHeight;

	private String bannerText;

	private String bannerLink;

	private String style;

	private int status;

	private boolean approved;

	private boolean manager;

	private boolean volunteer;

	private boolean commercial;

	private boolean canJoin;

	private boolean canApply;

	private DateTime lastPayerChangedAt;

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name = "wiki-name")
	public String getWikiName() {
		return wikiName;
	}

	public void setWikiName(String wikiName) {
		this.wikiName = wikiName;
	}

	@XmlElement(name = "public-permissions")
	public int getPublicPermissions() {
		return publicPermissions;
	}

	public void setPublicPermissions(int publicPermissions) {
		this.publicPermissions = publicPermissions;
	}

	@XmlElement(name = "team-permissions")
	public int getTeamPermissions() {
		return teamPermissions;
	}

	public void setTeamPermissions(int teamPermissions) {
		this.teamPermissions = teamPermissions;
	}

	@XmlElement(name = "watcher-permissions")
	public int getWatcherPermissions() {
		return watcherPermissions;
	}

	public void setWatcherPermissions(int watcherPermissions) {
		this.watcherPermissions = watcherPermissions;
	}

	@XmlElement(name = "share-permissions")
	public boolean isSharePermissions() {
		return sharePermissions;
	}

	public void setSharePermissions(boolean sharePermissions) {
		this.sharePermissions = sharePermissions;
	}

	@XmlElement(name = "team-tab-role")
	public int getTeamTabRole() {
		return teamTabRole;
	}

	public void setTeamTabRole(int teamTabRole) {
		this.teamTabRole = teamTabRole;
	}

	@XmlElement(name = "created-at")
	public DateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(DateTime createdAt) {
		this.createdAt = createdAt;
	}

	@XmlElement(name = "updated-at")
	public DateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(DateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@XmlElement(name = "default-showpage")
	public String getDefaultShowpage() {
		return defaultShowpage;
	}

	public void setDefaultShowpage(String defaultShowpage) {
		this.defaultShowpage = defaultShowpage;
	}

	@XmlElement(name = "tabs-order")
	public String getTabsOrder() {
		return tabsOrder;
	}

	public void setTabsOrder(String tabsOrder) {
		this.tabsOrder = tabsOrder;
	}

	@XmlElement(name = "parent-id")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@XmlElement
	public boolean isRestricted() {
		return restricted;
	}

	public void setRestricted(boolean restricted) {
		this.restricted = restricted;
	}

	@XmlElement(name = "restricted-date")
	public DateTime getRestrictedDate() {
		return restrictedDate;
	}

	public void setRestrictedDate(DateTime restrictedDate) {
		this.restrictedDate = restrictedDate;
	}

	@XmlElement(name = "commercial-from")
	public DateTime getCommercialFrom() {
		return commercialFrom;
	}

	public void setCommercialFrom(DateTime commercialFrom) {
		this.commercialFrom = commercialFrom;
	}

	@XmlElement(name = "banner")
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@XmlElement(name = "banner-height", nillable = true)
	public BigDecimal getBannerHeight() {
		return bannerHeight;
	}

	public void setBannerHeight(BigDecimal bannerHeight) {
		this.bannerHeight = bannerHeight;
	}

	@XmlElement(name = "banner-text")
	public String getBannerText() {
		return bannerText;
	}

	public void setBannerText(String bannerText) {
		this.bannerText = bannerText;
	}

	@XmlElement(name = "banner-link")
	public String getBannerLink() {
		return bannerLink;
	}

	public void setBannerLink(String bannerLink) {
		this.bannerLink = bannerLink;
	}

	@XmlElement(name = "style")
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@XmlElement(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@XmlElement(name = "approved")
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@XmlElement(name = "is-manager")
	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	@XmlElement(name = "is-volunteer")
	public boolean isVolunteer() {
		return volunteer;
	}

	public void setVolunteer(boolean volunteer) {
		this.volunteer = volunteer;
	}

	@XmlElement(name = "is-commercial")
	public boolean isCommercial() {
		return commercial;
	}

	public void setCommercial(boolean commercial) {
		this.commercial = commercial;
	}

	@XmlElement(name = "can-join")
	public boolean isCanJoin() {
		return canJoin;
	}

	public void setCanJoin(boolean canJoin) {
		this.canJoin = canJoin;
	}

	@XmlElement(name = "can-apply")
	public boolean isCanApply() {
		return canApply;
	}

	public void setCanApply(boolean canApply) {
		this.canApply = canApply;
	}

	@XmlElement(name = "last-payer-changed-at")
	public DateTime getLastPayerChangedAt() {
		return lastPayerChangedAt;
	}

	public void setLastPayerChangedAt(DateTime lastPayerChangedAt) {
		this.lastPayerChangedAt = lastPayerChangedAt;
	}

}
