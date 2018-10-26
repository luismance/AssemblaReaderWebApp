package com.webdrone.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.webdrone.assembla.dto.SpaceAssemblaDto;

@Entity
@Table(name = "SPACE")
public class Space extends RemoteEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "DESCRIPTION", columnDefinition = "TEXT", nullable = true)
	private String description;

	@Column(name = "WIKINAME", columnDefinition = "TEXT", nullable = true)
	private String wikiname;

	@Column(name = "PUBLIC_PERMISSIONS")
	private int publicPermissions;

	@Column(name = "TEAM_PERMISSIONS")
	private int teamPermissions;

	@Column(name = "WATCHER_PERMISSIONS")
	private int watcherPermissions;

	@Column(name = "TEAM_TAB_ROLE")
	private int teamTabRole;

	@Column(name = "DEFAULT_SHOW_PAGE", columnDefinition = "TEXT", nullable = true)
	private String defaultShowPage;

	@Column(name = "TABS_ORDER", columnDefinition = "TEXT", nullable = false)
	private String tabsOrder;

	@ManyToOne
	private Space parentSpace;

	@Column(name = "IS_RESTRICTED")
	private boolean isRestricted;

	@Column(name = "RESTRICTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date restrictedDate = new Date();

	@Column(name = "COMMERCIAL_FROM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date commercialFrom = new Date();

	@Column(name = "BANNER_PATH", columnDefinition = "TEXT")
	private String bannerPath;

	@Column(name = "BANNER_HEIGHT")
	private long bannerHeight;

	@Column(name = "BANNER_TEXT", columnDefinition = "TEXT")
	private String bannerText;

	@Column(name = "BANNER_LINK", columnDefinition = "TEXT")
	private String bannerLink;

	@Column(name = "STYLE", columnDefinition = "TEXT")
	private String style;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "IS_APPROVED")
	private boolean isApproved;

	@Column(name = "IS_MANAGER")
	private boolean isManager;

	@Column(name = "IS_VOLUNTEER")
	private boolean isVolunteer;

	@Column(name = "IS_COMMERCIAL")
	private boolean isCommercial;

	@Column(name = "CAN_JOIN")
	private boolean canJoin;

	@Column(name = "CAN_APPLY")
	private boolean canApply;

	@Column(name = "LAST_PAYER_CHANGED_AT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastPayerChangedAt = new Date();

	public Space() {
		super();
	}

	public Space(SpaceAssemblaDto spaceAssemblaDto) {
		super();
		this.bannerHeight = spaceAssemblaDto.getBannerHeight() != null ? spaceAssemblaDto.getBannerHeight().longValue()
				: 0;
		this.bannerLink = spaceAssemblaDto.getBannerLink();
		this.bannerPath = spaceAssemblaDto.getBanner();
		this.bannerText = spaceAssemblaDto.getBannerText();
		this.canApply = spaceAssemblaDto.isCanApply();
		this.canJoin = spaceAssemblaDto.isCanJoin();
		this.commercialFrom = spaceAssemblaDto.getCommercialFrom() != null ? spaceAssemblaDto.getCommercialFrom().toDate() : null;
		this.defaultShowPage = spaceAssemblaDto.getDefaultShowpage();
		this.description = spaceAssemblaDto.getDescription();
		this.isApproved = spaceAssemblaDto.isApproved();
		this.isCommercial = spaceAssemblaDto.isCommercial();
		this.isManager = spaceAssemblaDto.isManager();
		this.isRestricted = spaceAssemblaDto.isRestricted();
		this.isVolunteer = spaceAssemblaDto.isVolunteer();
		this.lastPayerChangedAt = spaceAssemblaDto.getLastPayerChangedAt() != null
				? spaceAssemblaDto.getLastPayerChangedAt().toDate() : null;
		// this.parentSpace = spaceAssemblaDto.getParentId();
		this.publicPermissions = spaceAssemblaDto.getPublicPermissions();
		this.restrictedDate = spaceAssemblaDto.getRestrictedDate() != null
				? spaceAssemblaDto.getRestrictedDate().toDate() : null;
		this.status = spaceAssemblaDto.getStatus();
		this.style = spaceAssemblaDto.getStyle();
		this.tabsOrder = spaceAssemblaDto.getTabsOrder();
		this.teamPermissions = spaceAssemblaDto.getTeamPermissions();
		this.teamTabRole = spaceAssemblaDto.getTeamTabRole();
		this.watcherPermissions = spaceAssemblaDto.getWatcherPermissions();
		this.wikiname = spaceAssemblaDto.getWikiName();
		this.setExternalRefId(spaceAssemblaDto.getId());
		this.setRemotelyCreated(spaceAssemblaDto.getCreatedAt()!= null ? spaceAssemblaDto.getCreatedAt().toDate() : new Date());
		this.setRemotelyUpdated(spaceAssemblaDto.getUpdatedAt()!= null ? spaceAssemblaDto.getUpdatedAt().toDate() : new Date());
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWikiname() {
		return wikiname;
	}

	public void setWikiname(String wikiname) {
		this.wikiname = wikiname;
	}

	public int getPublicPermissions() {
		return publicPermissions;
	}

	public void setPublicPermissions(int publicPermissions) {
		this.publicPermissions = publicPermissions;
	}

	public int getTeamPermissions() {
		return teamPermissions;
	}

	public void setTeamPermissions(int teamPermissions) {
		this.teamPermissions = teamPermissions;
	}

	public int getWatcherPermissions() {
		return watcherPermissions;
	}

	public void setWatcherPermissions(int watcherPermissions) {
		this.watcherPermissions = watcherPermissions;
	}

	public int getTeamTabRole() {
		return teamTabRole;
	}

	public void setTeamTabRole(int teamTabRole) {
		this.teamTabRole = teamTabRole;
	}

	public String getDefaultShowPage() {
		return defaultShowPage;
	}

	public void setDefaultShowPage(String defaultShowPage) {
		this.defaultShowPage = defaultShowPage;
	}

	public String getTabsOrder() {
		return tabsOrder;
	}

	public void setTabsOrder(String tabsOrder) {
		this.tabsOrder = tabsOrder;
	}

	public Space getParentSpace() {
		return parentSpace;
	}

	public void setParentSpace(Space parentSpace) {
		this.parentSpace = parentSpace;
	}

	public boolean isRestricted() {
		return isRestricted;
	}

	public void setRestricted(boolean isRestricted) {
		this.isRestricted = isRestricted;
	}

	public Date getRestrictedDate() {
		return restrictedDate;
	}

	public void setRestrictedDate(Date restrictedDate) {
		this.restrictedDate = restrictedDate;
	}

	public Date getCommercialFrom() {
		return commercialFrom;
	}

	public void setCommercialFrom(Date commercialFrom) {
		this.commercialFrom = commercialFrom;
	}

	public String getBannerPath() {
		return bannerPath;
	}

	public void setBannerPath(String bannerPath) {
		this.bannerPath = bannerPath;
	}

	public long getBannerHeight() {
		return bannerHeight;
	}

	public void setBannerHeight(long bannerHeight) {
		this.bannerHeight = bannerHeight;
	}

	public String getBannerText() {
		return bannerText;
	}

	public void setBannerText(String bannerText) {
		this.bannerText = bannerText;
	}

	public String getBannerLink() {
		return bannerLink;
	}

	public void setBannerLink(String bannerLink) {
		this.bannerLink = bannerLink;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	public boolean isVolunteer() {
		return isVolunteer;
	}

	public void setVolunteer(boolean isVolunteer) {
		this.isVolunteer = isVolunteer;
	}

	public boolean isCommercial() {
		return isCommercial;
	}

	public void setCommercial(boolean isCommercial) {
		this.isCommercial = isCommercial;
	}

	public boolean isCanJoin() {
		return canJoin;
	}

	public void setCanJoin(boolean canJoin) {
		this.canJoin = canJoin;
	}

	public boolean isCanApply() {
		return canApply;
	}

	public void setCanApply(boolean canApply) {
		this.canApply = canApply;
	}

	public Date getLastPayerChangedAt() {
		return lastPayerChangedAt;
	}

	public void setLastPayerChangedAt(Date lastPayerChangedAt) {
		this.lastPayerChangedAt = lastPayerChangedAt;
	}

}
