[CompilationUnitImpl][CtCommentImpl]/* Campaign.java

Copyright (c) 2009 Jay Lawson <jaylawson39 at yahoo.com>. All rights reserved.

This file is part of MekHQ.

MekHQ is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

MekHQ is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with MekHQ. If not, see <http://www.gnu.org/licenses/>.
 */
[CtPackageDeclarationImpl]package mekhq.campaign;
[CtUnresolvedImport]import mekhq.campaign.universe.AbstractFactionSelector;
[CtUnresolvedImport]import megamek.common.util.BuildingBlock;
[CtUnresolvedImport]import mekhq.campaign.personnel.generator.DefaultPersonnelGenerator;
[CtImportImpl]import java.time.LocalDate;
[CtUnresolvedImport]import mekhq.campaign.againstTheBot.AtBConfiguration;
[CtUnresolvedImport]import mekhq.campaign.parts.Armor;
[CtUnresolvedImport]import mekhq.campaign.parts.MissingMekActuator;
[CtUnresolvedImport]import mekhq.campaign.market.ShoppingList;
[CtUnresolvedImport]import mekhq.campaign.event.AcquisitionEvent;
[CtUnresolvedImport]import mekhq.campaign.mission.atb.AtBScenarioFactory;
[CtUnresolvedImport]import mekhq.campaign.event.PartWorkEvent;
[CtUnresolvedImport]import mekhq.campaign.event.GMModeEvent;
[CtUnresolvedImport]import megamek.common.options.IOption;
[CtUnresolvedImport]import mekhq.campaign.universe.RangedPlanetSelector;
[CtUnresolvedImport]import mekhq.campaign.mod.am.InjuryUtil;
[CtUnresolvedImport]import mekhq.campaign.parts.MissingPart;
[CtUnresolvedImport]import megamek.common.util.EncodeControl;
[CtUnresolvedImport]import mekhq.campaign.event.UnitNewEvent;
[CtUnresolvedImport]import mekhq.campaign.universe.Faction;
[CtUnresolvedImport]import mekhq.campaign.personnel.enums.Phenotype;
[CtUnresolvedImport]import mekhq.campaign.finances.*;
[CtUnresolvedImport]import megamek.common.options.IBasicOption;
[CtUnresolvedImport]import mekhq.campaign.unit.CrewType;
[CtUnresolvedImport]import mekhq.campaign.personnel.*;
[CtUnresolvedImport]import mekhq.campaign.parts.Refit;
[CtImportImpl]import mekhq.*;
[CtUnresolvedImport]import mekhq.campaign.unit.TestUnit;
[CtUnresolvedImport]import mekhq.campaign.universe.NewsItem;
[CtUnresolvedImport]import mekhq.campaign.event.MissionRemovedEvent;
[CtUnresolvedImport]import mekhq.campaign.universe.Planet;
[CtUnresolvedImport]import mekhq.campaign.event.ReportEvent;
[CtUnresolvedImport]import mekhq.campaign.personnel.enums.PrisonerStatus;
[CtUnresolvedImport]import mekhq.campaign.universe.News;
[CtUnresolvedImport]import megamek.common.options.GameOptions;
[CtUnresolvedImport]import mekhq.campaign.event.PartNewEvent;
[CtUnresolvedImport]import mekhq.campaign.parts.equipment.EquipmentPart;
[CtImportImpl]import java.io.Serializable;
[CtUnresolvedImport]import mekhq.campaign.event.OrganizationChangedEvent;
[CtUnresolvedImport]import mekhq.campaign.universe.DefaultPlanetSelector;
[CtImportImpl]import java.util.*;
[CtUnresolvedImport]import mekhq.campaign.event.AstechPoolChangedEvent;
[CtUnresolvedImport]import mekhq.campaign.parts.AmmoStorage;
[CtUnresolvedImport]import megamek.common.loaders.EntityLoadingException;
[CtUnresolvedImport]import mekhq.campaign.parts.BaArmor;
[CtUnresolvedImport]import mekhq.campaign.unit.UnitOrder;
[CtUnresolvedImport]import mekhq.campaign.againstTheBot.enums.AtBLanceRole;
[CtUnresolvedImport]import mekhq.campaign.event.LoanPaidEvent;
[CtUnresolvedImport]import mekhq.service.AutosaveService;
[CtUnresolvedImport]import mekhq.gui.utilities.PortraitFileFactory;
[CtUnresolvedImport]import mekhq.campaign.event.ScenarioRemovedEvent;
[CtUnresolvedImport]import mekhq.campaign.universe.RATGeneratorConnector;
[CtImportImpl]import java.io.PrintWriter;
[CtUnresolvedImport]import mekhq.campaign.universe.Era;
[CtUnresolvedImport]import mekhq.campaign.rating.CampaignOpsReputation;
[CtUnresolvedImport]import mekhq.campaign.event.DeploymentChangedEvent;
[CtUnresolvedImport]import mekhq.campaign.universe.PlanetarySystem;
[CtUnresolvedImport]import megamek.common.annotations.Nullable;
[CtUnresolvedImport]import mekhq.campaign.event.MedicPoolChangedEvent;
[CtUnresolvedImport]import mekhq.campaign.parts.SpacecraftCoolingSystem;
[CtUnresolvedImport]import mekhq.campaign.mission.Mission;
[CtUnresolvedImport]import mekhq.campaign.market.ContractMarket;
[CtUnresolvedImport]import mekhq.campaign.parts.PartInUse;
[CtUnresolvedImport]import mekhq.campaign.work.IAcquisitionWork;
[CtUnresolvedImport]import mekhq.campaign.event.LoanNewEvent;
[CtUnresolvedImport]import megamek.client.generator.RandomUnitGenerator;
[CtImportImpl]import java.util.function.Predicate;
[CtUnresolvedImport]import mekhq.campaign.unit.Unit;
[CtUnresolvedImport]import mekhq.campaign.event.PartChangedEvent;
[CtUnresolvedImport]import mekhq.campaign.event.PersonChangedEvent;
[CtUnresolvedImport]import mekhq.campaign.mission.AtBContract;
[CtUnresolvedImport]import mekhq.campaign.event.MissionNewEvent;
[CtUnresolvedImport]import mekhq.campaign.event.UnitRemovedEvent;
[CtUnresolvedImport]import megamek.common.*;
[CtUnresolvedImport]import mekhq.campaign.rating.IUnitRating;
[CtUnresolvedImport]import mekhq.campaign.market.UnitMarket;
[CtUnresolvedImport]import mekhq.service.IAutosaveService;
[CtUnresolvedImport]import mekhq.campaign.parts.StructuralIntegrity;
[CtImportImpl]import java.util.stream.Stream;
[CtUnresolvedImport]import mekhq.campaign.event.LocationChangedEvent;
[CtUnresolvedImport]import mekhq.campaign.universe.Systems;
[CtUnresolvedImport]import megamek.common.options.OptionsConstants;
[CtImportImpl]import javax.swing.JOptionPane;
[CtUnresolvedImport]import mekhq.campaign.parts.equipment.MissingEquipmentPart;
[CtUnresolvedImport]import mekhq.campaign.rating.FieldManualMercRevDragoonsRating;
[CtUnresolvedImport]import mekhq.campaign.force.Force;
[CtUnresolvedImport]import megamek.common.enums.Gender;
[CtUnresolvedImport]import mekhq.campaign.log.*;
[CtUnresolvedImport]import mekhq.campaign.universe.RATManager;
[CtUnresolvedImport]import megamek.common.loaders.BLKFile;
[CtUnresolvedImport]import mekhq.campaign.event.NewDayEvent;
[CtUnresolvedImport]import mekhq.campaign.parts.MekLocation;
[CtUnresolvedImport]import mekhq.campaign.event.ScenarioNewEvent;
[CtUnresolvedImport]import mekhq.campaign.mission.AtBScenario;
[CtUnresolvedImport]import mekhq.campaign.universe.RandomFactionGenerator;
[CtImportImpl]import java.io.File;
[CtUnresolvedImport]import mekhq.campaign.parts.PartInventory;
[CtUnresolvedImport]import mekhq.campaign.mission.Contract;
[CtUnresolvedImport]import mekhq.campaign.event.NetworkChangedEvent;
[CtUnresolvedImport]import mekhq.campaign.market.PersonnelMarket;
[CtUnresolvedImport]import megamek.common.options.IOptionGroup;
[CtUnresolvedImport]import mekhq.campaign.rating.UnitRatingMethod;
[CtUnresolvedImport]import mekhq.campaign.event.PartRemovedEvent;
[CtUnresolvedImport]import megamek.utils.MegaMekXmlUtil;
[CtUnresolvedImport]import mekhq.campaign.event.OvertimeModeEvent;
[CtUnresolvedImport]import mekhq.campaign.parts.Part;
[CtUnresolvedImport]import mekhq.campaign.universe.DefaultFactionSelector;
[CtUnresolvedImport]import mekhq.campaign.universe.RangedFactionSelector;
[CtUnresolvedImport]import mekhq.campaign.personnel.generator.AbstractPersonnelGenerator;
[CtUnresolvedImport]import mekhq.campaign.unit.UnitTechProgression;
[CtUnresolvedImport]import mekhq.campaign.parts.OmniPod;
[CtUnresolvedImport]import mekhq.campaign.work.IPartWork;
[CtUnresolvedImport]import megamek.client.generator.RandomGenderGenerator;
[CtUnresolvedImport]import mekhq.campaign.event.PartArrivedEvent;
[CtUnresolvedImport]import mekhq.campaign.force.Lance;
[CtImportImpl]import java.text.MessageFormat;
[CtUnresolvedImport]import mekhq.campaign.event.DayEndingEvent;
[CtUnresolvedImport]import mekhq.service.MassRepairService;
[CtUnresolvedImport]import mekhq.campaign.mission.Scenario;
[CtImportImpl]import java.time.DayOfWeek;
[CtUnresolvedImport]import mekhq.campaign.universe.IUnitGenerator;
[CtUnresolvedImport]import mekhq.campaign.event.PersonRemovedEvent;
[CtUnresolvedImport]import mekhq.campaign.market.PartsStore;
[CtUnresolvedImport]import mekhq.module.atb.AtBEventProcessor;
[CtImportImpl]import java.time.temporal.ChronoUnit;
[CtUnresolvedImport]import mekhq.campaign.parts.ProtomekArmor;
[CtUnresolvedImport]import mekhq.campaign.universe.AbstractPlanetSelector;
[CtUnresolvedImport]import mekhq.campaign.event.PersonNewEvent;
[CtUnresolvedImport]import mekhq.campaign.personnel.enums.PersonnelStatus;
[CtUnresolvedImport]import megamek.common.util.fileUtils.DirectoryItems;
[CtUnresolvedImport]import megamek.client.generator.RandomNameGenerator;
[CtUnresolvedImport]import mekhq.campaign.parts.equipment.AmmoBin;
[CtClassImpl][CtJavaDocImpl]/**
 * The main campaign class, keeps track of teams and units
 *
 * @author Taharqa
 */
public class Campaign implements [CtTypeReferenceImpl]java.io.Serializable , [CtTypeReferenceImpl]ITechManager {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String REPORT_LINEBREAK = [CtLiteralImpl]"<br/><br/>";

    [CtFieldImpl]private static final [CtTypeReferenceImpl]long serialVersionUID = [CtUnaryOperatorImpl]-[CtLiteralImpl]6312434701389973056L;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.UUID id;

    [CtFieldImpl][CtCommentImpl]// we have three things to track: (1) teams, (2) units, (3) repair tasks
    [CtCommentImpl]// we will use the same basic system (borrowed from MegaMek) for tracking
    [CtCommentImpl]// all three
    [CtCommentImpl]// OK now we have more, parts, personnel, forces, missions, and scenarios.
    [CtCommentImpl]// and more still - we're tracking DropShips and WarShips in a separate set so that we can assign units to transports
    private [CtTypeReferenceImpl]mekhq.campaign.Hangar units = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Hangar();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.util.UUID> transportShips = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]Person> personnel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedHashMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.TreeMap<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]mekhq.campaign.parts.Part> parts = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.TreeMap<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]mekhq.campaign.force.Force> forceIds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.TreeMap<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]mekhq.campaign.mission.Mission> missions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.TreeMap<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]mekhq.campaign.mission.Scenario> scenarios = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.TreeMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Kill>> kills = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Integer> duplicateNameHash = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]int astechPool;

    [CtFieldImpl]private [CtTypeReferenceImpl]int astechPoolMinutes;

    [CtFieldImpl]private [CtTypeReferenceImpl]int astechPoolOvertime;

    [CtFieldImpl]private [CtTypeReferenceImpl]int medicPool;

    [CtFieldImpl]private [CtTypeReferenceImpl]int lastPartId;

    [CtFieldImpl]private [CtTypeReferenceImpl]int lastForceId;

    [CtFieldImpl]private [CtTypeReferenceImpl]int lastMissionId;

    [CtFieldImpl]private [CtTypeReferenceImpl]int lastScenarioId;

    [CtFieldImpl][CtCommentImpl]// I need to put a basic game object in campaign so that I can
    [CtCommentImpl]// assign it to the entities, otherwise some entity methods may get NPE
    [CtCommentImpl]// if they try to call up game options
    private [CtTypeReferenceImpl]mekhq.campaign.Game game;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.Player player;

    [CtFieldImpl]private [CtTypeReferenceImpl]megamek.common.options.GameOptions gameOptions;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String name;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.time.LocalDate currentDay;

    [CtFieldImpl][CtCommentImpl]// hierarchically structured Force object to define TO&E
    private [CtTypeReferenceImpl]mekhq.campaign.force.Force forces;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.Hashtable<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]mekhq.campaign.force.Lance> lances;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String factionCode;

    [CtFieldImpl]private [CtTypeReferenceImpl]int techFactionCode;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String retainerEmployerCode;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.Ranks ranks;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> currentReport;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.lang.String currentReportHTML;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> newReports;

    [CtFieldImpl][CtCommentImpl]// this is updated and used per gaming session, it is enabled/disabled via the Campaign options
    [CtCommentImpl]// we're re-using the LogEntry class that is used to store Personnel entries
    public [CtTypeReferenceImpl]java.util.LinkedList<[CtTypeReferenceImpl]LogEntry> inMemoryLogHistory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.LinkedList<>();

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean overtime;

    [CtFieldImpl]private [CtTypeReferenceImpl]boolean gmMode;

    [CtFieldImpl]private transient [CtTypeReferenceImpl]boolean overviewLoadingValue = [CtLiteralImpl]true;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String camoCategory = [CtFieldReadImpl]Player.NO_CAMO;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String camoFileName = [CtLiteralImpl]null;

    [CtFieldImpl]private [CtTypeReferenceImpl]int colorIndex = [CtLiteralImpl]0;

    [CtFieldImpl][CtCommentImpl]// unit icon
    public static final [CtTypeReferenceImpl]java.lang.String ROOT_ICON = [CtLiteralImpl]"-- General --";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String ICON_NONE = [CtLiteralImpl]"None";

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String iconCategory = [CtFieldReadImpl]mekhq.campaign.Campaign.ROOT_ICON;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String iconFileName = [CtFieldReadImpl]mekhq.campaign.Campaign.ICON_NONE;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.Finances finances;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.CurrentLocation location;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.universe.News news;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.market.PartsStore partsStore;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> customs;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.CampaignOptions campaignOptions;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.RandomSkillPreferences rskillPrefs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]RandomSkillPreferences();

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.MekHQ app;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList shoppingList;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.market.PersonnelMarket personnelMarket;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.market.ContractMarket contractMarket;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.market.UnitMarket unitMarket;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.RetirementDefectionTracker retirementDefectionTracker;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]int fatigueLevel;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.againstTheBot.AtBConfiguration atbConfig;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.module.atb.AtBEventProcessor atbEventProcessor;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]java.time.LocalDate shipSearchStart;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]int shipSearchType;

    [CtFieldImpl]private [CtTypeReferenceImpl]java.lang.String shipSearchResult;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]java.time.LocalDate shipSearchExpiration;[CtCommentImpl]// AtB


    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.universe.IUnitGenerator unitGenerator;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.rating.IUnitRating unitRating;

    [CtFieldImpl]private [CtTypeReferenceImpl]mekhq.campaign.CampaignSummary campaignSummary;

    [CtFieldImpl]private final [CtTypeReferenceImpl]java.util.ResourceBundle resources = [CtInvocationImpl][CtTypeAccessImpl]java.util.ResourceBundle.getBundle([CtLiteralImpl]"mekhq.resources.Campaign", [CtConstructorCallImpl]new [CtTypeReferenceImpl]megamek.common.util.EncodeControl());

    [CtFieldImpl][CtJavaDocImpl]/**
     * This is used to determine if the player has an active AtB Contract, and is recalculated on load
     */
    private transient [CtTypeReferenceImpl]boolean hasActiveContract;

    [CtFieldImpl]private final [CtTypeReferenceImpl]mekhq.service.IAutosaveService autosaveService;

    [CtConstructorImpl]public Campaign() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]id = [CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID();
        [CtAssignmentImpl][CtFieldWriteImpl]game = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Game();
        [CtAssignmentImpl][CtFieldWriteImpl]player = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Player([CtLiteralImpl]0, [CtLiteralImpl]"self");
        [CtInvocationImpl][CtFieldReadImpl]game.addPlayer([CtLiteralImpl]0, [CtFieldReadImpl]player);
        [CtAssignmentImpl][CtFieldWriteImpl]currentDay = [CtInvocationImpl][CtTypeAccessImpl]java.time.LocalDate.ofYearDay([CtLiteralImpl]3067, [CtLiteralImpl]1);
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]CurrencyManager.getInstance().setCampaign([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]location = [CtConstructorCallImpl]new [CtTypeReferenceImpl]CurrentLocation([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystems().get([CtLiteralImpl]"Outreach"), [CtLiteralImpl]0);
        [CtAssignmentImpl][CtFieldWriteImpl]campaignOptions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]CampaignOptions();
        [CtAssignmentImpl][CtFieldWriteImpl]currentReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]currentReportHTML = [CtLiteralImpl]"";
        [CtAssignmentImpl][CtFieldWriteImpl]newReports = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]name = [CtLiteralImpl]"My Campaign";
        [CtAssignmentImpl][CtFieldWriteImpl]overtime = [CtLiteralImpl]false;
        [CtAssignmentImpl][CtFieldWriteImpl]gmMode = [CtLiteralImpl]false;
        [CtAssignmentImpl][CtFieldWriteImpl]factionCode = [CtLiteralImpl]"MERC";
        [CtAssignmentImpl][CtFieldWriteImpl]techFactionCode = [CtFieldReadImpl]ITechnology.F_MERC;
        [CtAssignmentImpl][CtFieldWriteImpl]retainerEmployerCode = [CtLiteralImpl]null;
        [CtInvocationImpl][CtTypeAccessImpl]Ranks.initializeRankSystems();
        [CtAssignmentImpl][CtFieldWriteImpl]ranks = [CtInvocationImpl][CtTypeAccessImpl]Ranks.getRanksFromSystem([CtTypeAccessImpl]Ranks.RS_SL);
        [CtAssignmentImpl][CtFieldWriteImpl]forces = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.force.Force([CtFieldReadImpl]name);
        [CtInvocationImpl][CtFieldReadImpl]forceIds.put([CtLiteralImpl]0, [CtFieldReadImpl]forces);
        [CtAssignmentImpl][CtFieldWriteImpl]lances = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Hashtable<>();
        [CtAssignmentImpl][CtFieldWriteImpl]finances = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Finances();
        [CtInvocationImpl][CtTypeAccessImpl]SkillType.initializeTypes();
        [CtInvocationImpl][CtTypeAccessImpl]SpecialAbility.initializeSPA();
        [CtAssignmentImpl][CtFieldWriteImpl]astechPool = [CtLiteralImpl]0;
        [CtAssignmentImpl][CtFieldWriteImpl]medicPool = [CtLiteralImpl]0;
        [CtInvocationImpl]resetAstechMinutes();
        [CtAssignmentImpl][CtFieldWriteImpl]partsStore = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.market.PartsStore([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtFieldWriteImpl]gameOptions = [CtConstructorCallImpl]new [CtTypeReferenceImpl]megamek.common.options.GameOptions();
        [CtInvocationImpl][CtFieldReadImpl]gameOptions.initialize();
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]gameOptions.getOption([CtLiteralImpl]"year").setValue([CtInvocationImpl]getGameYear());
        [CtInvocationImpl][CtFieldReadImpl]game.setOptions([CtFieldReadImpl]gameOptions);
        [CtAssignmentImpl][CtFieldWriteImpl]customs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtAssignmentImpl][CtFieldWriteImpl]shoppingList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList();
        [CtAssignmentImpl][CtFieldWriteImpl]news = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.News([CtInvocationImpl]getGameYear(), [CtInvocationImpl][CtFieldReadImpl]id.getLeastSignificantBits());
        [CtAssignmentImpl][CtFieldWriteImpl]personnelMarket = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.market.PersonnelMarket();
        [CtAssignmentImpl][CtFieldWriteImpl]contractMarket = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.market.ContractMarket();
        [CtAssignmentImpl][CtFieldWriteImpl]unitMarket = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.market.UnitMarket();
        [CtAssignmentImpl][CtFieldWriteImpl]retirementDefectionTracker = [CtConstructorCallImpl]new [CtTypeReferenceImpl]RetirementDefectionTracker();
        [CtAssignmentImpl][CtFieldWriteImpl]fatigueLevel = [CtLiteralImpl]0;
        [CtAssignmentImpl][CtFieldWriteImpl]atbConfig = [CtLiteralImpl]null;
        [CtAssignmentImpl][CtFieldWriteImpl]autosaveService = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.service.AutosaveService();
        [CtAssignmentImpl][CtFieldWriteImpl]hasActiveContract = [CtLiteralImpl]false;
        [CtAssignmentImpl][CtFieldWriteImpl]campaignSummary = [CtConstructorCallImpl]new [CtTypeReferenceImpl]CampaignSummary([CtThisAccessImpl]this);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the app
     */
    public [CtTypeReferenceImpl]mekhq.campaign.MekHQ getApp() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]app;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param app
     * 		the app to set
     */
    public [CtTypeReferenceImpl]void setApp([CtParameterImpl][CtTypeReferenceImpl]MekHQ app) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.app = [CtVariableReadImpl]app;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the overviewLoadingValue
     */
    public [CtTypeReferenceImpl]boolean isOverviewLoadingValue() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]overviewLoadingValue;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param overviewLoadingValue
     * 		the overviewLoadingValue to set
     */
    public [CtTypeReferenceImpl]void setOverviewLoadingValue([CtParameterImpl][CtTypeReferenceImpl]boolean overviewLoadingValue) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.overviewLoadingValue = [CtVariableReadImpl]overviewLoadingValue;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Game getGame() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]game;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Player getPlayer() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]player;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setId([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.id = [CtVariableReadImpl]id;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.UUID getId() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]id;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]name;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.name = [CtVariableReadImpl]s;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getEraName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Era.getEraNameFromYear([CtInvocationImpl]getGameYear());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getEra() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Era.getEra([CtInvocationImpl]getGameYear());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTitle() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getName() + [CtLiteralImpl]" (") + [CtInvocationImpl]getFactionName()) + [CtLiteralImpl]")") + [CtLiteralImpl]" - ") + [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getMekHQOptions().getLongDisplayFormattedDate([CtInvocationImpl]getLocalDate())) + [CtLiteralImpl]" (") + [CtInvocationImpl]getEraName()) + [CtLiteralImpl]")";
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.time.LocalDate getLocalDate() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]currentDay;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setLocalDate([CtParameterImpl][CtTypeReferenceImpl]java.time.LocalDate currentDay) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.currentDay = [CtVariableReadImpl]currentDay;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem getCurrentSystem() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]location.getCurrentSystem();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getFunds() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]finances.getBalance();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setForces([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force f) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]forces = [CtVariableReadImpl]f;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.force.Force getForces() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]forces;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void importLance([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Lance l) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]lances.put([CtInvocationImpl][CtVariableReadImpl]l.getForceId(), [CtVariableReadImpl]l);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Hashtable<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]mekhq.campaign.force.Lance> getLances() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]lances;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.force.Lance> getLanceList() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.force.Lance> retVal = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Lance l : [CtInvocationImpl][CtFieldReadImpl]lances.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]forceIds.containsKey([CtInvocationImpl][CtVariableReadImpl]l.getForceId())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]retVal.add([CtVariableReadImpl]l);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]retVal;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setShoppingList([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList sl) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]shoppingList = [CtVariableReadImpl]sl;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList getShoppingList() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]shoppingList;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setPersonnelMarket([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.market.PersonnelMarket pm) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]personnelMarket = [CtVariableReadImpl]pm;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.market.PersonnelMarket getPersonnelMarket() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]personnelMarket;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void generateNewPersonnelMarket() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]personnelMarket.generatePersonnelForDay([CtThisAccessImpl]this);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setContractMarket([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.market.ContractMarket cm) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]contractMarket = [CtVariableReadImpl]cm;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.market.ContractMarket getContractMarket() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]contractMarket;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void generateNewContractMarket() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]contractMarket.generateContractOffers([CtThisAccessImpl]this);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setUnitMarket([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.market.UnitMarket um) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]unitMarket = [CtVariableReadImpl]um;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.market.UnitMarket getUnitMarket() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]unitMarket;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void generateNewUnitMarket() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]unitMarket.generateUnitOffers([CtThisAccessImpl]this);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRetirementDefectionTracker([CtParameterImpl][CtTypeReferenceImpl]RetirementDefectionTracker rdt) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]retirementDefectionTracker = [CtVariableReadImpl]rdt;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.RetirementDefectionTracker getRetirementDefectionTracker() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]retirementDefectionTracker;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setFatigueLevel([CtParameterImpl][CtTypeReferenceImpl]int fl) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]fatigueLevel = [CtVariableReadImpl]fl;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getFatigueLevel() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]fatigueLevel;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Initializes the unit generator based on the method chosen in campaignOptions.
     * Called when the unit generator is first used or when the method has been
     * changed in campaignOptions.
     */
    public [CtTypeReferenceImpl]void initUnitGenerator() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]unitGenerator != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtFieldReadImpl]unitGenerator instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.universe.RATManager)) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.unregisterHandler([CtFieldReadImpl]unitGenerator);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]campaignOptions.useStaticRATs()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.RATManager rm = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.RATManager();
            [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.generator.RandomUnitGenerator.getInstance().isInitialized()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]50);
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException e) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().error([CtThisAccessImpl]this, [CtVariableReadImpl]e);
                }
            } 
            [CtInvocationImpl][CtVariableReadImpl]rm.setSelectedRATs([CtInvocationImpl][CtFieldReadImpl]campaignOptions.getRATs());
            [CtInvocationImpl][CtVariableReadImpl]rm.setIgnoreRatEra([CtInvocationImpl][CtFieldReadImpl]campaignOptions.canIgnoreRatEra());
            [CtAssignmentImpl][CtFieldWriteImpl]unitGenerator = [CtVariableReadImpl]rm;
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]unitGenerator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.RATGeneratorConnector([CtInvocationImpl]getGameYear());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return - the class responsible for generating random units
     */
    public [CtTypeReferenceImpl]mekhq.campaign.universe.IUnitGenerator getUnitGenerator() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]unitGenerator == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl]initUnitGenerator();
        }
        [CtReturnImpl]return [CtFieldReadImpl]unitGenerator;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setAtBEventProcessor([CtParameterImpl][CtTypeReferenceImpl]mekhq.module.atb.AtBEventProcessor processor) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]atbEventProcessor = [CtVariableReadImpl]processor;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setAtBConfig([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.againstTheBot.AtBConfiguration config) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]atbConfig = [CtVariableReadImpl]config;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.againstTheBot.AtBConfiguration getAtBConfig() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]atbConfig == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]atbConfig = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.againstTheBot.AtBConfiguration.loadFromXml();
        }
        [CtReturnImpl]return [CtFieldReadImpl]atbConfig;
    }

    [CtMethodImpl][CtCommentImpl]// region Ship Search
    [CtJavaDocImpl]/**
     * Sets the date a ship search was started, or null if no search is in progress.
     */
    public [CtTypeReferenceImpl]void setShipSearchStart([CtParameterImpl][CtAnnotationImpl]@megamek.common.annotations.Nullable
    [CtTypeReferenceImpl]java.time.LocalDate shipSearchStart) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.shipSearchStart = [CtVariableReadImpl]shipSearchStart;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The date a ship search was started, or null if none is in progress.
     */
    public [CtTypeReferenceImpl]java.time.LocalDate getShipSearchStart() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]shipSearchStart;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the lookup name of the available ship, or null if none were found.
     */
    public [CtTypeReferenceImpl]void setShipSearchResult([CtParameterImpl][CtAnnotationImpl]@megamek.common.annotations.Nullable
    [CtTypeReferenceImpl]java.lang.String result) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]shipSearchResult = [CtVariableReadImpl]result;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The lookup name of the available ship, or null if none is available
     */
    public [CtTypeReferenceImpl]java.lang.String getShipSearchResult() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]shipSearchResult;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The date the ship is no longer available, if there is one.
     */
    public [CtTypeReferenceImpl]java.time.LocalDate getShipSearchExpiration() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]shipSearchExpiration;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setShipSearchExpiration([CtParameterImpl][CtTypeReferenceImpl]java.time.LocalDate shipSearchExpiration) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.shipSearchExpiration = [CtVariableReadImpl]shipSearchExpiration;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the unit type to search for.
     */
    public [CtTypeReferenceImpl]void setShipSearchType([CtParameterImpl][CtTypeReferenceImpl]int unitType) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]shipSearchType = [CtVariableReadImpl]unitType;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void startShipSearch([CtParameterImpl][CtTypeReferenceImpl]int unitType) [CtBlockImpl]{
        [CtInvocationImpl]setShipSearchStart([CtInvocationImpl]getLocalDate());
        [CtInvocationImpl]setShipSearchType([CtVariableReadImpl]unitType);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processShipSearch() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getShipSearchStart() == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder report = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getFinances().debit([CtInvocationImpl][CtInvocationImpl]getAtBConfig().shipSearchCostPerWeek(), [CtTypeAccessImpl]Transaction.C_UNIT, [CtLiteralImpl]"Ship search", [CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]report.append([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getAtBConfig().shipSearchCostPerWeek().toAmountAndSymbolString()).append([CtLiteralImpl]" deducted for ship search.");
        } else [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtLiteralImpl]"<font color=\"red\">Insufficient funds for ship search.</font>");
            [CtInvocationImpl]setShipSearchStart([CtLiteralImpl]null);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]long numDays = [CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.time.temporal.ChronoUnit.[CtFieldReferenceImpl]DAYS.between([CtInvocationImpl]getShipSearchStart(), [CtInvocationImpl]getLocalDate());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]numDays > [CtLiteralImpl]21) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
            [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtInvocationImpl][CtInvocationImpl]getAtBConfig().shipSearchTargetRoll([CtFieldReadImpl]shipSearchType, [CtThisAccessImpl]this);
            [CtInvocationImpl]setShipSearchStart([CtLiteralImpl]null);
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]report.append([CtLiteralImpl]"<br/>Ship search target: ").append([CtInvocationImpl][CtVariableReadImpl]target.getValueAsString()).append([CtLiteralImpl]" roll: ").append([CtVariableReadImpl]roll);
            [CtIfImpl][CtCommentImpl]// TODO: mos zero should make ship available on retainer
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]roll >= [CtInvocationImpl][CtVariableReadImpl]target.getValue()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]report.append([CtLiteralImpl]"<br/>Search successful. ");
                [CtLocalVariableImpl][CtTypeReferenceImpl]MechSummary ms = [CtInvocationImpl][CtFieldReadImpl]unitGenerator.generate([CtInvocationImpl]getFactionCode(), [CtFieldReadImpl]shipSearchType, [CtUnaryOperatorImpl]-[CtLiteralImpl]1, [CtInvocationImpl]getGameYear(), [CtInvocationImpl]getUnitRatingMod());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ms == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]ms = [CtInvocationImpl][CtInvocationImpl]getAtBConfig().findShip([CtFieldReadImpl]shipSearchType);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ms != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl]setShipSearchResult([CtInvocationImpl][CtVariableReadImpl]ms.getName());
                    [CtInvocationImpl]setShipSearchExpiration([CtInvocationImpl][CtInvocationImpl]getLocalDate().plusDays([CtLiteralImpl]31));
                    [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]report.append([CtInvocationImpl]getShipSearchResult()).append([CtLiteralImpl]" is available for purchase for ").append([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Money.of([CtInvocationImpl][CtVariableReadImpl]ms.getCost()).toAmountAndSymbolString()).append([CtLiteralImpl]" until ").append([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getMekHQOptions().getDisplayFormattedDate([CtInvocationImpl]getShipSearchExpiration()));
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]report.append([CtLiteralImpl]" <font color=\"red\">Could not determine ship type.</font>");
                }
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]report.append([CtLiteralImpl]"<br/>Ship search unsuccessful.");
            }
        }
        [CtInvocationImpl]addReport([CtInvocationImpl][CtVariableReadImpl]report.toString());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void purchaseShipSearchResult() [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String METHOD_NAME = [CtLiteralImpl]"purchaseShipSearchResult()";
        [CtLocalVariableImpl][CtTypeReferenceImpl]MechSummary ms = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MechSummaryCache.getInstance().getMech([CtInvocationImpl]getShipSearchResult());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ms == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().error([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtLiteralImpl]"Cannot find entry for " + [CtInvocationImpl]getShipSearchResult());
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money cost = [CtInvocationImpl][CtTypeAccessImpl]Money.of([CtInvocationImpl][CtVariableReadImpl]ms.getCost());
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getFunds().isLessThan([CtVariableReadImpl]cost)) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtLiteralImpl]"<font color='red'><b> You cannot afford this unit. Transaction cancelled</b>.</font>");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]MechFileParser mechFileParser;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]mechFileParser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MechFileParser([CtInvocationImpl][CtVariableReadImpl]ms.getSourceFile(), [CtInvocationImpl][CtVariableReadImpl]ms.getEntryName());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception ex) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().error([CtThisAccessImpl]this, [CtBinaryOperatorImpl][CtLiteralImpl]"Unable to load unit: " + [CtInvocationImpl][CtVariableReadImpl]ms.getEntryName(), [CtVariableReadImpl]ex);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]mechFileParser.getEntity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int transitDays = [CtConditionalImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getInstantUnitMarketDelivery()) ? [CtLiteralImpl]0 : [CtInvocationImpl]calculatePartTransitTime([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2) - [CtLiteralImpl]2);
        [CtInvocationImpl][CtInvocationImpl]getFinances().debit([CtVariableReadImpl]cost, [CtTypeAccessImpl]Transaction.C_UNIT, [CtBinaryOperatorImpl][CtLiteralImpl]"Purchased " + [CtInvocationImpl][CtVariableReadImpl]en.getShortName(), [CtInvocationImpl]getLocalDate());
        [CtInvocationImpl]addNewUnit([CtVariableReadImpl]en, [CtLiteralImpl]true, [CtVariableReadImpl]transitDays);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getInstantUnitMarketDelivery()) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='green'>Unit will be delivered in " + [CtVariableReadImpl]transitDays) + [CtLiteralImpl]" days.</font>");
        }
        [CtInvocationImpl]setShipSearchResult([CtLiteralImpl]null);
        [CtInvocationImpl]setShipSearchExpiration([CtLiteralImpl]null);
    }

    [CtMethodImpl][CtCommentImpl]// endregion Ship Search
    [CtJavaDocImpl]/**
     * Process retirements for retired personnel, if any.
     *
     * @param totalPayout
     * 		The total retirement payout.
     * @param unitAssignments
     * 		List of unit assignments.
     * @return False if there were payments AND they were unable to be processed, true otherwise.
     */
    public [CtTypeReferenceImpl]boolean applyRetirement([CtParameterImpl][CtTypeReferenceImpl]Money totalPayout, [CtParameterImpl][CtTypeReferenceImpl]java.util.HashMap<[CtTypeReferenceImpl]java.util.UUID, [CtTypeReferenceImpl]java.util.UUID> unitAssignments) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]totalPayout.isPositive() || [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().getRetirees())) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getFinances().debit([CtVariableReadImpl]totalPayout, [CtTypeAccessImpl]Transaction.C_SALARY, [CtLiteralImpl]"Final Payout", [CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID pid : [CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().getRetirees()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getPerson([CtVariableReadImpl]pid).getStatus().isActive()) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl]getPerson([CtVariableReadImpl]pid).changeStatus([CtThisAccessImpl]this, [CtTypeAccessImpl]PersonnelStatus.RETIRED);
                        [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getPerson([CtVariableReadImpl]pid).getFullName() + [CtLiteralImpl]" has retired.");
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]Person.T_NONE != [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().getPayout([CtVariableReadImpl]pid).getRecruitType()) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl]getPersonnelMarket().addPerson([CtInvocationImpl]newPerson([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().getPayout([CtVariableReadImpl]pid).getRecruitType()));
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().getPayout([CtVariableReadImpl]pid).hasHeir()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]Person p = [CtInvocationImpl]newPerson([CtInvocationImpl][CtInvocationImpl]getPerson([CtVariableReadImpl]pid).getPrimaryRole());
                        [CtInvocationImpl][CtVariableReadImpl]p.setOriginalUnitWeight([CtInvocationImpl][CtInvocationImpl]getPerson([CtVariableReadImpl]pid).getOriginalUnitWeight());
                        [CtInvocationImpl][CtVariableReadImpl]p.setOriginalUnitTech([CtInvocationImpl][CtInvocationImpl]getPerson([CtVariableReadImpl]pid).getOriginalUnitTech());
                        [CtInvocationImpl][CtVariableReadImpl]p.setOriginalUnitId([CtInvocationImpl][CtInvocationImpl]getPerson([CtVariableReadImpl]pid).getOriginalUnitId());
                        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]unitAssignments.containsKey([CtVariableReadImpl]pid)) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl]getPersonnelMarket().addPerson([CtVariableReadImpl]p, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtInvocationImpl][CtVariableReadImpl]unitAssignments.get([CtVariableReadImpl]pid)).getEntity());
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl]getPersonnelMarket().addPerson([CtVariableReadImpl]p);
                        }
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().canAtBAddDependents()) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int dependents = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().getPayout([CtVariableReadImpl]pid).getDependents();
                        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]dependents > [CtLiteralImpl]0) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]Person person = [CtInvocationImpl]newDependent([CtTypeAccessImpl]Person.T_ASTECH, [CtLiteralImpl]false);
                            [CtIfImpl]if ([CtInvocationImpl]recruitPerson([CtVariableReadImpl]person)) [CtBlockImpl]{
                                [CtUnaryOperatorImpl][CtVariableWriteImpl]dependents--;
                            } else [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]dependents = [CtLiteralImpl]0;
                            }
                        } 
                    }
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]unitAssignments.containsKey([CtVariableReadImpl]pid)) [CtBlockImpl]{
                        [CtInvocationImpl]removeUnit([CtInvocationImpl][CtVariableReadImpl]unitAssignments.get([CtVariableReadImpl]pid));
                    }
                }
                [CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().resolveAllContracts();
                [CtReturnImpl]return [CtLiteralImpl]true;
            } else [CtBlockImpl]{
                [CtInvocationImpl]addReport([CtLiteralImpl]"<font color='red'>You cannot afford to make the final payments.</font>");
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.CampaignSummary getCampaignSummary() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]campaignSummary;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.universe.News getNews() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]news;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add force to an existing superforce. This method will also assign the force an id and place it in the forceId hash
     *
     * @param force
     * 		- the Force to add
     * @param superForce
     * 		- the superforce to add the new force to
     */
    public [CtTypeReferenceImpl]void addForce([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force force, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force superForce) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int id = [CtBinaryOperatorImpl][CtFieldReadImpl]lastForceId + [CtLiteralImpl]1;
        [CtInvocationImpl][CtVariableReadImpl]force.setId([CtVariableReadImpl]id);
        [CtInvocationImpl][CtVariableReadImpl]superForce.addSubForce([CtVariableReadImpl]force, [CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]force.setScenarioId([CtInvocationImpl][CtVariableReadImpl]superForce.getScenarioId());
        [CtInvocationImpl][CtFieldReadImpl]forceIds.put([CtVariableReadImpl]id, [CtVariableReadImpl]force);
        [CtAssignmentImpl][CtFieldWriteImpl]lastForceId = [CtVariableReadImpl]id;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]campaignOptions.getUseAtB() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]force.getUnits().size() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtInvocationImpl][CtFieldReadImpl]lances.get([CtVariableReadImpl]id)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]lances.put([CtVariableReadImpl]id, [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.force.Lance([CtInvocationImpl][CtVariableReadImpl]force.getId(), [CtThisAccessImpl]this));
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void moveForce([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force force, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force superForce) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force parentForce = [CtInvocationImpl][CtVariableReadImpl]force.getParentForce();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]parentForce) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]parentForce.removeSubForce([CtInvocationImpl][CtVariableReadImpl]force.getId());
        }
        [CtInvocationImpl][CtVariableReadImpl]superForce.addSubForce([CtVariableReadImpl]force, [CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]force.setScenarioId([CtInvocationImpl][CtVariableReadImpl]superForce.getScenarioId());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object o : [CtInvocationImpl][CtVariableReadImpl]force.getAllChildren([CtThisAccessImpl]this)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.unit.Unit) (o)).setScenarioId([CtInvocationImpl][CtVariableReadImpl]superForce.getScenarioId());
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.force.Force) (o)).setScenarioId([CtInvocationImpl][CtVariableReadImpl]superForce.getScenarioId());
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is used by the XML loader. The id should already be set for this force so dont increment
     *
     * @param force
     */
    public [CtTypeReferenceImpl]void importForce([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force force) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]lastForceId = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtFieldReadImpl]lastForceId, [CtInvocationImpl][CtVariableReadImpl]force.getId());
        [CtInvocationImpl][CtFieldReadImpl]forceIds.put([CtInvocationImpl][CtVariableReadImpl]force.getId(), [CtVariableReadImpl]force);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is used by the XML loader. The id should already be set for this scenario so dont increment
     *
     * @param scenario
     */
    public [CtTypeReferenceImpl]void importScenario([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Scenario scenario) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]lastScenarioId = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtFieldReadImpl]lastScenarioId, [CtInvocationImpl][CtVariableReadImpl]scenario.getId());
        [CtInvocationImpl][CtFieldReadImpl]scenarios.put([CtInvocationImpl][CtVariableReadImpl]scenario.getId(), [CtVariableReadImpl]scenario);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add unit to an existing force. This method will also assign that force's id to the unit.
     *
     * @param u
     * @param id
     */
    public [CtTypeReferenceImpl]void addUnitToForce([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u, [CtParameterImpl][CtTypeReferenceImpl]int id) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force prevForce = [CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtInvocationImpl][CtVariableReadImpl]u.getForceId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]prevForce) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]prevForce.removeUnit([CtInvocationImpl][CtVariableReadImpl]u.getId());
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.OrganizationChangedEvent([CtVariableReadImpl]prevForce, [CtVariableReadImpl]u));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]prevForce.getTechID()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]u.removeTech();
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force force = [CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtVariableReadImpl]id);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]force) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]u.setForceId([CtVariableReadImpl]id);
            [CtInvocationImpl][CtVariableReadImpl]force.addUnit([CtInvocationImpl][CtVariableReadImpl]u.getId());
            [CtInvocationImpl][CtVariableReadImpl]u.setScenarioId([CtInvocationImpl][CtVariableReadImpl]force.getScenarioId());
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.OrganizationChangedEvent([CtVariableReadImpl]force, [CtVariableReadImpl]u));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]force.getTechID()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Person forceTech = [CtInvocationImpl]getPerson([CtInvocationImpl][CtVariableReadImpl]force.getTechID());
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]forceTech.canTech([CtInvocationImpl][CtVariableReadImpl]u.getEntity())) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]u.getTech()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]u.removeTech();
                    }
                    [CtInvocationImpl][CtVariableReadImpl]u.setTech([CtVariableReadImpl]forceTech);
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String cantTech = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]forceTech.getFullName() + [CtLiteralImpl]" cannot maintain ") + [CtInvocationImpl][CtVariableReadImpl]u.getName()) + [CtLiteralImpl]"\n") + [CtLiteralImpl]"You will need to assign a tech manually.";
                    [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtLiteralImpl]null, [CtVariableReadImpl]cantTech, [CtLiteralImpl]"Warning", [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]WARNING_MESSAGE);
                }
            }
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]campaignOptions.getUseAtB()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]prevForce) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]prevForce.getUnits().size() == [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]lances.remove([CtInvocationImpl][CtVariableReadImpl]prevForce.getId());
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtInvocationImpl][CtFieldReadImpl]lances.get([CtVariableReadImpl]id)) && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]force)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]lances.put([CtVariableReadImpl]id, [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.force.Lance([CtInvocationImpl][CtVariableReadImpl]force.getId(), [CtThisAccessImpl]this));
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds force and all its subforces to the AtB lance table
     */
    private [CtTypeReferenceImpl]void addAllLances([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force force) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]force.getUnits().size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]lances.put([CtInvocationImpl][CtVariableReadImpl]force.getId(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.force.Lance([CtInvocationImpl][CtVariableReadImpl]force.getId(), [CtThisAccessImpl]this));
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force f : [CtInvocationImpl][CtVariableReadImpl]force.getSubForces()) [CtBlockImpl]{
            [CtInvocationImpl]addAllLances([CtVariableReadImpl]f);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a mission to the campaign
     *
     * @param m
     * 		The mission to be added
     */
    public [CtTypeReferenceImpl]void addMission([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission m) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int id = [CtBinaryOperatorImpl][CtFieldReadImpl]lastMissionId + [CtLiteralImpl]1;
        [CtInvocationImpl][CtVariableReadImpl]m.setId([CtVariableReadImpl]id);
        [CtInvocationImpl][CtFieldReadImpl]missions.put([CtVariableReadImpl]id, [CtVariableReadImpl]m);
        [CtAssignmentImpl][CtFieldWriteImpl]lastMissionId = [CtVariableReadImpl]id;
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.MissionNewEvent([CtVariableReadImpl]m));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Imports a {@link Mission} into a campaign.
     *
     * @param m
     * 		Mission to import into the campaign.
     */
    public [CtTypeReferenceImpl]void importMission([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission m) [CtBlockImpl]{
        [CtForEachImpl][CtCommentImpl]// add scenarios to the scenarioId hash
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Scenario s : [CtInvocationImpl][CtVariableReadImpl]m.getScenarios()) [CtBlockImpl]{
            [CtInvocationImpl]importScenario([CtVariableReadImpl]s);
        }
        [CtInvocationImpl]addMissionWithoutId([CtVariableReadImpl]m);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addMissionWithoutId([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission m) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]lastMissionId = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtFieldReadImpl]lastMissionId, [CtInvocationImpl][CtVariableReadImpl]m.getId());
        [CtInvocationImpl][CtFieldReadImpl]missions.put([CtInvocationImpl][CtVariableReadImpl]m.getId(), [CtVariableReadImpl]m);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.MissionNewEvent([CtVariableReadImpl]m));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return an <code>Collection</code> of missions in the campaign
     */
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]mekhq.campaign.mission.Mission> getMissions() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]missions.values();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return missions ArrayList sorted with complete missions at the bottom
     */
    public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.mission.Mission> getSortedMissions() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.mission.Mission> msns = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtFieldReadImpl]missions.values());
        [CtInvocationImpl][CtVariableReadImpl]msns.sort([CtLambdaImpl]([CtParameterImpl] m1,[CtParameterImpl] m2) -> [CtInvocationImpl][CtTypeAccessImpl]java.lang.Boolean.compare([CtInvocationImpl][CtVariableReadImpl]m2.isActive(), [CtInvocationImpl][CtVariableReadImpl]m1.isActive()));
        [CtReturnImpl]return [CtVariableReadImpl]msns;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param id
     * 		the <code>int</code> id of the team
     * @return a <code>SupportTeam</code> object
     */
    public [CtTypeReferenceImpl]mekhq.campaign.mission.Mission getMission([CtParameterImpl][CtTypeReferenceImpl]int id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]missions.get([CtVariableReadImpl]id);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add scenario to an existing mission. This method will also assign the scenario an id, provided
     * that it is a new scenario. It then adds the scenario to the scenarioId hash.
     *
     * Scenarios with previously set ids can be sent to this mission, allowing one to remove
     * and then re-add scenarios if needed. This functionality is used in the
     * <code>AtBScenarioFactory</code> class in method <code>createScenariosForNewWeek</code> to
     * ensure that scenarios are generated properly.
     *
     * @param s
     * 		- the Scenario to add
     * @param m
     * 		- the mission to add the new scenario to
     */
    public [CtTypeReferenceImpl]void addScenario([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Scenario s, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission m) [CtBlockImpl]{
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]boolean newScenario = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]s.getId() == [CtFieldReadImpl]mekhq.campaign.mission.Scenario.S_DEFAULT_ID;
        [CtLocalVariableImpl]final [CtTypeReferenceImpl]int id = [CtConditionalImpl]([CtVariableReadImpl]newScenario) ? [CtUnaryOperatorImpl]++[CtFieldWriteImpl]lastScenarioId : [CtInvocationImpl][CtVariableReadImpl]s.getId();
        [CtInvocationImpl][CtVariableReadImpl]s.setId([CtVariableReadImpl]id);
        [CtInvocationImpl][CtVariableReadImpl]m.addScenario([CtVariableReadImpl]s);
        [CtInvocationImpl][CtFieldReadImpl]scenarios.put([CtVariableReadImpl]id, [CtVariableReadImpl]s);
        [CtIfImpl]if ([CtVariableReadImpl]newScenario) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtFieldReadImpl]resources.getString([CtLiteralImpl]"newAtBMission.format"), [CtInvocationImpl][CtVariableReadImpl]s.getName(), [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getMekHQOptions().getDisplayFormattedDate([CtInvocationImpl][CtVariableReadImpl]s.getDate())));
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.ScenarioNewEvent([CtVariableReadImpl]s));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.mission.Scenario getScenario([CtParameterImpl][CtTypeReferenceImpl]int id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]scenarios.get([CtVariableReadImpl]id);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setLocation([CtParameterImpl][CtTypeReferenceImpl]CurrentLocation l) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]location = [CtVariableReadImpl]l;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Moves immediately to a {@link PlanetarySystem}.
     *
     * @param s
     * 		The {@link PlanetarySystem} the campaign
     * 		has been moved to.
     */
    public [CtTypeReferenceImpl]void moveToPlanetarySystem([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem s) [CtBlockImpl]{
        [CtInvocationImpl]setLocation([CtConstructorCallImpl]new [CtTypeReferenceImpl]CurrentLocation([CtVariableReadImpl]s, [CtLiteralImpl]0.0));
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.LocationChangedEvent([CtInvocationImpl]getLocation(), [CtLiteralImpl]false));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.CurrentLocation getLocation() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]location;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Imports a {@link Unit} into a campaign.
     *
     * @param u
     * 		A {@link Unit} to import into the campaign.
     */
    public [CtTypeReferenceImpl]void importUnit([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().debug([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Importing unit: (" + [CtInvocationImpl][CtVariableReadImpl]u.getId()) + [CtLiteralImpl]"): ") + [CtInvocationImpl][CtVariableReadImpl]u.getName());
        [CtInvocationImpl][CtInvocationImpl]getHangar().addUnit([CtVariableReadImpl]u);
        [CtInvocationImpl]checkDuplicateNamesDuringAdd([CtInvocationImpl][CtVariableReadImpl]u.getEntity());
        [CtIfImpl][CtCommentImpl]// If this is a ship, add it to the list of potential transports
        [CtCommentImpl]// Jumpships and space stations are intentionally ignored at present, because this functionality is being
        [CtCommentImpl]// used to auto-load ground units into bays, and doing this for large craft that can't transit is pointless.
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]u.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Dropship) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]u.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Warship)) [CtBlockImpl]{
            [CtInvocationImpl]addTransportShip([CtInvocationImpl][CtVariableReadImpl]u.getId());
        }
        [CtIfImpl][CtCommentImpl]// Assign an entity ID to our new unit
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]Entity.NONE == [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().getId()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().setId([CtInvocationImpl][CtFieldReadImpl]game.getNextEntityId());
        }
        [CtInvocationImpl][CtFieldReadImpl]game.addEntity([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().getId(), [CtInvocationImpl][CtVariableReadImpl]u.getEntity());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds an entry to the list of transit-capable transport ships. We'll use this
     * to look for empty bays that ground units can be assigned to
     *
     * @param id
     * 		- The unique ID of the ship we want to add to this Set
     */
    public [CtTypeReferenceImpl]void addTransportShip([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().debug([CtBinaryOperatorImpl][CtLiteralImpl]"Adding DropShip/WarShip: " + [CtVariableReadImpl]id);
        [CtInvocationImpl][CtFieldReadImpl]transportShips.add([CtVariableReadImpl]id);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Deletes an entry from the list of transit-capable transport ships. This gets updated when
     * the ship is removed from the campaign for one reason or another
     *
     * @param id
     * 		- The unique ID of the ship we want to remove from this Set
     */
    public [CtTypeReferenceImpl]void removeTransportShip([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().debug([CtBinaryOperatorImpl][CtLiteralImpl]"Removing DropShip/WarShip: " + [CtVariableReadImpl]id);
        [CtInvocationImpl][CtFieldReadImpl]transportShips.remove([CtVariableReadImpl]id);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is for adding a TestUnit that was previously created and had parts added to
     * it. We need to do the normal stuff, but we also need to take the existing parts and
     * add them to the campaign.
     *
     * @param tu
     */
    public [CtTypeReferenceImpl]void addTestUnit([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.TestUnit tu) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// we really just want the entity and the parts so lets just wrap that around a
        [CtCommentImpl]// new
        [CtCommentImpl]// unit.
        [CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtInvocationImpl][CtVariableReadImpl]tu.getEntity(), [CtThisAccessImpl]this);
        [CtInvocationImpl][CtInvocationImpl]getHangar().addUnit([CtVariableReadImpl]unit);
        [CtInvocationImpl][CtCommentImpl]// we decided we like the test unit so much we are going to keep it
        [CtInvocationImpl][CtVariableReadImpl]unit.getEntity().setOwner([CtFieldReadImpl]player);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().setGame([CtFieldReadImpl]game);
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().setExternalIdAsString([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getId().toString());
        [CtForEachImpl][CtCommentImpl]// now lets grab the parts from the test unit and set them up with this unit
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p : [CtInvocationImpl][CtVariableReadImpl]tu.getParts()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]unit.addPart([CtVariableReadImpl]p);
            [CtInvocationImpl]addPart([CtVariableReadImpl]p, [CtLiteralImpl]0);
        }
        [CtInvocationImpl][CtVariableReadImpl]unit.resetPilotAndEntity();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]unit.isRepairable()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]unit.setSalvage([CtLiteralImpl]true);
        }
        [CtIfImpl][CtCommentImpl]// Assign an entity ID to our new unit
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]Entity.NONE == [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getId()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().setId([CtInvocationImpl][CtFieldReadImpl]game.getNextEntityId());
        }
        [CtInvocationImpl][CtFieldReadImpl]game.addEntity([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getId(), [CtInvocationImpl][CtVariableReadImpl]unit.getEntity());
        [CtInvocationImpl]checkDuplicateNamesDuringAdd([CtInvocationImpl][CtVariableReadImpl]unit.getEntity());
        [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getHyperlinkedName() + [CtLiteralImpl]" has been added to the unit roster.");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Add a new unit to the campaign.
     *
     * @param en
     * 		An <code>Entity</code> object that the new unit will be wrapped around
     */
    public [CtTypeReferenceImpl]mekhq.campaign.unit.Unit addNewUnit([CtParameterImpl][CtTypeReferenceImpl]Entity en, [CtParameterImpl][CtTypeReferenceImpl]boolean allowNewPilots, [CtParameterImpl][CtTypeReferenceImpl]int days) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtVariableReadImpl]en, [CtThisAccessImpl]this);
        [CtInvocationImpl][CtInvocationImpl]getHangar().addUnit([CtVariableReadImpl]unit);
        [CtInvocationImpl][CtCommentImpl]// reset the game object
        [CtVariableReadImpl]en.setOwner([CtFieldReadImpl]player);
        [CtInvocationImpl][CtVariableReadImpl]en.setGame([CtFieldReadImpl]game);
        [CtInvocationImpl][CtVariableReadImpl]en.setExternalIdAsString([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getId().toString());
        [CtInvocationImpl][CtVariableReadImpl]unit.initializeBaySpace();
        [CtInvocationImpl]removeUnitFromForce([CtVariableReadImpl]unit);[CtCommentImpl]// Added to avoid the 'default force bug'

        [CtIfImpl][CtCommentImpl]// when calculating cargo
        [CtCommentImpl]// If this is a ship, add it to the list of potential transports
        [CtCommentImpl]// Jumpships and space stations are intentionally ignored at present, because this functionality is being
        [CtCommentImpl]// used to auto-load ground units into bays, and doing this for large craft that can't transit is pointless.
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Dropship) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Warship)) [CtBlockImpl]{
            [CtInvocationImpl]addTransportShip([CtFieldReadImpl]id);
        }
        [CtInvocationImpl][CtVariableReadImpl]unit.initializeParts([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]unit.runDiagnostic([CtLiteralImpl]false);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]unit.isRepairable()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]unit.setSalvage([CtLiteralImpl]true);
        }
        [CtInvocationImpl][CtVariableReadImpl]unit.setDaysToArrival([CtVariableReadImpl]days);
        [CtIfImpl]if ([CtVariableReadImpl]allowNewPilots) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]mekhq.campaign.unit.CrewType, [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]Person>> newCrew = [CtInvocationImpl][CtTypeAccessImpl]Utilities.genRandomCrewWithCombinedSkill([CtThisAccessImpl]this, [CtVariableReadImpl]unit, [CtInvocationImpl]getFactionCode());
            [CtInvocationImpl][CtVariableReadImpl]newCrew.forEach([CtLambdaImpl]([CtParameterImpl] type,[CtParameterImpl] personnel) -> [CtInvocationImpl][CtVariableReadImpl]personnel.forEach([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]type.addMethod.accept([CtVariableReadImpl]unit, [CtVariableReadImpl]p)));
        }
        [CtInvocationImpl][CtVariableReadImpl]unit.resetPilotAndEntity();
        [CtIfImpl][CtCommentImpl]// Assign an entity ID to our new unit
        if ([CtBinaryOperatorImpl][CtFieldReadImpl]Entity.NONE == [CtInvocationImpl][CtVariableReadImpl]en.getId()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]en.setId([CtInvocationImpl][CtFieldReadImpl]game.getNextEntityId());
        }
        [CtInvocationImpl][CtFieldReadImpl]game.addEntity([CtInvocationImpl][CtVariableReadImpl]en.getId(), [CtVariableReadImpl]en);
        [CtInvocationImpl]checkDuplicateNamesDuringAdd([CtVariableReadImpl]en);
        [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getHyperlinkedName() + [CtLiteralImpl]" has been added to the unit roster.");
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.UnitNewEvent([CtVariableReadImpl]unit));
        [CtReturnImpl]return [CtVariableReadImpl]unit;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the current hangar containing the player's units.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Hangar getHangar() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]units;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> getUnits() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getHangar().getUnits();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]Entity> getEntities() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]Entity> entities = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]entities.add([CtInvocationImpl][CtVariableReadImpl]unit.getEntity());
        }
        [CtReturnImpl]return [CtVariableReadImpl]entities;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.unit.Unit getUnit([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]id);
    }

    [CtMethodImpl][CtCommentImpl]// region Personnel
    [CtCommentImpl]// region Person Creation
    [CtJavaDocImpl]/**
     * Creates a new {@link Person}, who is a dependent, of a given primary role.
     *
     * @param type
     * 		The primary role of the {@link Person}, e.g. {@link Person#T_MECHWARRIOR}.
     * @return A new {@link Person} of the given primary role, who is a dependent.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Person newDependent([CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]boolean baby) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Person person;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]baby) && [CtInvocationImpl][CtFieldReadImpl]campaignOptions.getRandomizeDependentOrigin()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]person = [CtInvocationImpl]newPerson([CtVariableReadImpl]type);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]person = [CtInvocationImpl]newPerson([CtVariableReadImpl]type, [CtTypeAccessImpl]Person.T_NONE, [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.DefaultFactionSelector(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.DefaultPlanetSelector(), [CtTypeAccessImpl]Gender.RANDOMIZE);
        }
        [CtInvocationImpl][CtVariableReadImpl]person.setDependent([CtLiteralImpl]true);
        [CtReturnImpl]return [CtVariableReadImpl]person;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generate a new pilotPerson of the given type using whatever randomization options have been given in the
     * CampaignOptions
     *
     * @param type
     * 		The primary role
     * @return A new {@link Person}.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Person newPerson([CtParameterImpl][CtTypeReferenceImpl]int type) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]newPerson([CtVariableReadImpl]type, [CtTypeAccessImpl]Person.T_NONE);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generate a new pilotPerson of the given type using whatever randomization options have been given in the
     * CampaignOptions
     *
     * @param type
     * 		The primary role
     * @param secondary
     * 		A secondary role; used for LAM pilots to generate MW + Aero pilot
     * @return A new {@link Person}.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Person newPerson([CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]int secondary) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]newPerson([CtVariableReadImpl]type, [CtVariableReadImpl]secondary, [CtInvocationImpl]getFactionSelector(), [CtInvocationImpl]getPlanetSelector(), [CtTypeAccessImpl]Gender.RANDOMIZE);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generate a new pilotPerson of the given type using whatever randomization options have been given in the
     * CampaignOptions
     *
     * @param type
     * 		The primary role
     * @param secondary
     * 		A secondary role; used for LAM pilots to generate MW + Aero pilot
     * @param factionCode
     * 		The code for the faction this person is to be generated from
     * @param gender
     * 		The gender of the person to be generated, or a randomize it value
     * @return A new {@link Person}.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Person newPerson([CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]int secondary, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String factionCode, [CtParameterImpl][CtTypeReferenceImpl]megamek.common.enums.Gender gender) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]newPerson([CtVariableReadImpl]type, [CtVariableReadImpl]secondary, [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.DefaultFactionSelector([CtVariableReadImpl]factionCode), [CtInvocationImpl]getPlanetSelector(), [CtVariableReadImpl]gender);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generate a new pilotPerson of the given type using whatever randomization options have been given in the
     * CampaignOptions
     *
     * @param type
     * 		The primary role
     * @param secondary
     * 		A secondary role; used for LAM pilots to generate MW + Aero pilot
     * @param factionSelector
     * 		The faction selector to use for the person.
     * @param planetSelector
     * 		The planet selector for the person.
     * @param gender
     * 		The gender of the person to be generated, or a randomize it value
     * @return A new {@link Person}.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Person newPerson([CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]int secondary, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.AbstractFactionSelector factionSelector, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.AbstractPlanetSelector planetSelector, [CtParameterImpl][CtTypeReferenceImpl]megamek.common.enums.Gender gender) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.personnel.generator.AbstractPersonnelGenerator personnelGenerator = [CtInvocationImpl]getPersonnelGenerator([CtVariableReadImpl]factionSelector, [CtVariableReadImpl]planetSelector);
        [CtReturnImpl]return [CtInvocationImpl]newPerson([CtVariableReadImpl]type, [CtVariableReadImpl]secondary, [CtVariableReadImpl]personnelGenerator, [CtVariableReadImpl]gender);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Generate a new {@link Person} of the given type, using the supplied {@link AbstractPersonnelGenerator}
     *
     * @param type
     * 		The primary role of the {@link Person}.
     * @param secondary
     * 		The secondary role, or {@link Person#T_NONE}, of the {@link Person}.
     * @param personnelGenerator
     * 		The {@link AbstractPersonnelGenerator} to use when creating the {@link Person}.
     * @param gender
     * 		The gender of the person to be generated, or a randomize it value
     * @return A new {@link Person} configured using {@code personnelGenerator}.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Person newPerson([CtParameterImpl][CtTypeReferenceImpl]int type, [CtParameterImpl][CtTypeReferenceImpl]int secondary, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.personnel.generator.AbstractPersonnelGenerator personnelGenerator, [CtParameterImpl][CtTypeReferenceImpl]megamek.common.enums.Gender gender) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]Person.T_LAM_PILOT) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]type = [CtFieldReadImpl]Person.T_MECHWARRIOR;
            [CtAssignmentImpl][CtVariableWriteImpl]secondary = [CtFieldReadImpl]Person.T_AERO_PILOT;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Person person = [CtInvocationImpl][CtVariableReadImpl]personnelGenerator.generate([CtThisAccessImpl]this, [CtVariableReadImpl]type, [CtVariableReadImpl]secondary, [CtVariableReadImpl]gender);
        [CtIfImpl][CtCommentImpl]// Assign a random portrait after we generate a new person
        if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usePortraitForType([CtVariableReadImpl]type)) [CtBlockImpl]{
            [CtInvocationImpl]assignRandomPortraitFor([CtVariableReadImpl]person);
        }
        [CtReturnImpl]return [CtVariableReadImpl]person;
    }

    [CtMethodImpl][CtCommentImpl]// endregion Person Creation
    [CtCommentImpl]// region Personnel Recruitment
    [CtJavaDocImpl]/**
     *
     * @param p
     * 		the person being added
     * @return true if the person is hired successfully, otherwise false
     */
    public [CtTypeReferenceImpl]boolean recruitPerson([CtParameterImpl][CtTypeReferenceImpl]Person p) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]recruitPerson([CtVariableReadImpl]p, [CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus(), [CtInvocationImpl][CtVariableReadImpl]p.isDependent(), [CtLiteralImpl]false, [CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param p
     * 		the person being added
     * @param gmAdd
     * 		false means that they need to pay to hire this person, provided that
     * 		the campaign option to pay for new hires is set, while
     * 		true means they are added without paying
     * @return true if the person is hired successfully, otherwise false
     */
    public [CtTypeReferenceImpl]boolean recruitPerson([CtParameterImpl][CtTypeReferenceImpl]Person p, [CtParameterImpl][CtTypeReferenceImpl]boolean gmAdd) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]recruitPerson([CtVariableReadImpl]p, [CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus(), [CtInvocationImpl][CtVariableReadImpl]p.isDependent(), [CtVariableReadImpl]gmAdd, [CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param p
     * 		the person being added
     * @param prisonerStatus
     * 		the person's prisoner status upon recruitment
     * @return true if the person is hired successfully, otherwise false
     */
    public [CtTypeReferenceImpl]boolean recruitPerson([CtParameterImpl][CtTypeReferenceImpl]Person p, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.personnel.enums.PrisonerStatus prisonerStatus) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]recruitPerson([CtVariableReadImpl]p, [CtVariableReadImpl]prisonerStatus, [CtInvocationImpl][CtVariableReadImpl]p.isDependent(), [CtLiteralImpl]false, [CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @param p
     * 		the person being added
     * @param prisonerStatus
     * 		the person's prisoner status upon recruitment
     * @param dependent
     * 		if the person is a dependent or not. True means they are a dependent
     * @param gmAdd
     * 		false means that they need to pay to hire this person, true means it is added without paying
     * @param log
     * 		whether or not to write to logs
     * @return true if the person is hired successfully, otherwise false
     */
    public [CtTypeReferenceImpl]boolean recruitPerson([CtParameterImpl][CtTypeReferenceImpl]Person p, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.personnel.enums.PrisonerStatus prisonerStatus, [CtParameterImpl][CtTypeReferenceImpl]boolean dependent, [CtParameterImpl][CtTypeReferenceImpl]boolean gmAdd, [CtParameterImpl][CtTypeReferenceImpl]boolean log) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl][CtCommentImpl]// Only pay if option set, they weren't GM added, and they aren't a dependent, prisoner or bondsman
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().payForRecruitment() && [CtUnaryOperatorImpl](![CtVariableReadImpl]dependent)) && [CtUnaryOperatorImpl](![CtVariableReadImpl]gmAdd)) && [CtInvocationImpl][CtVariableReadImpl]prisonerStatus.isFree()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getFinances().debit([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getSalary().multipliedBy([CtLiteralImpl]2), [CtTypeAccessImpl]Transaction.C_SALARY, [CtBinaryOperatorImpl][CtLiteralImpl]"Recruitment of " + [CtInvocationImpl][CtVariableReadImpl]p.getFullName(), [CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='red'><b>Insufficient funds to recruit " + [CtInvocationImpl][CtVariableReadImpl]p.getFullName()) + [CtLiteralImpl]"</b></font>");
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]personnel.put([CtInvocationImpl][CtVariableReadImpl]p.getId(), [CtVariableReadImpl]p);
        [CtIfImpl]if ([CtVariableReadImpl]log) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String add = [CtConditionalImpl]([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]prisonerStatus.isFree()) ? [CtConditionalImpl][CtInvocationImpl][CtVariableReadImpl]prisonerStatus.isBondsman() ? [CtLiteralImpl]" as a bondsman" : [CtLiteralImpl]" as a prisoner" : [CtLiteralImpl]"";
            [CtInvocationImpl]addReport([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s has been added to the personnel roster%s.", [CtInvocationImpl][CtVariableReadImpl]p.getHyperlinkedName(), [CtVariableReadImpl]add));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_ASTECH) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes += [CtLiteralImpl]480;
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolOvertime += [CtLiteralImpl]240;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getSecondaryRole() == [CtFieldReadImpl]Person.T_ASTECH) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes += [CtLiteralImpl]240;
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolOvertime += [CtLiteralImpl]120;
        }
        [CtInvocationImpl][CtVariableReadImpl]p.setPrisonerStatus([CtVariableReadImpl]prisonerStatus, [CtVariableReadImpl]log);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PersonNewEvent([CtVariableReadImpl]p));
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtCommentImpl]// endregion Personnel Recruitment
    [CtCommentImpl]// region Bloodnames
    [CtJavaDocImpl]/**
     * If the person does not already have a bloodname, assigns a chance of having one based on
     * skill and rank. If the roll indicates there should be a bloodname, one is assigned as
     * appropriate to the person's phenotype and the player's faction.
     *
     * @param person
     * 		The Bloodname candidate
     * @param ignoreDice
     * 		If true, skips the random roll and assigns a Bloodname automatically
     */
    public [CtTypeReferenceImpl]void checkBloodnameAdd([CtParameterImpl][CtTypeReferenceImpl]Person person, [CtParameterImpl][CtTypeReferenceImpl]boolean ignoreDice) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// if a non-clanner or a clanner without a phenotype is here, we can just return
        if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]person.isClanner()) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]person.getPhenotype() == [CtFieldReadImpl]mekhq.campaign.personnel.enums.Phenotype.NONE)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtIfImpl][CtCommentImpl]// Person already has a bloodname, we open up the dialog to ask if they want to keep the
        [CtCommentImpl]// current bloodname or assign a new one
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getBloodname().length() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int result = [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showConfirmDialog([CtLiteralImpl]null, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]person.getFullTitle() + [CtLiteralImpl]" already has the bloodname ") + [CtInvocationImpl][CtVariableReadImpl]person.getBloodname()) + [CtLiteralImpl]"\nDo you wish to remove that bloodname and generate a new one?", [CtLiteralImpl]"Already Has Bloodname", [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_NO_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]QUESTION_MESSAGE);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]result == [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]NO_OPTION) [CtBlockImpl]{
                [CtReturnImpl]return;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]ignoreDice = [CtLiteralImpl]true;
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// Go ahead and generate a new bloodname
        [CtTypeReferenceImpl]int bloodnameTarget = [CtLiteralImpl]6;
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]ignoreDice) [CtBlockImpl]{
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]person.getPhenotype()) {
                [CtCaseImpl]case [CtFieldReadImpl]MECHWARRIOR :
                    [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_GUN_MECH)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_GUN_MECH).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_PILOT_MECH)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_PILOT_MECH).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case [CtFieldReadImpl]AEROSPACE :
                    [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_GUN_AERO)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_GUN_AERO).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_PILOT_AERO)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_PILOT_AERO).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case [CtFieldReadImpl]ELEMENTAL :
                    [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_GUN_BA)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_GUN_BA).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_ANTI_MECH)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_ANTI_MECH).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case [CtFieldReadImpl]VEHICLE :
                    [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_GUN_VEE)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_GUN_VEE).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]person.getPrimaryRole()) {
                            [CtCaseImpl]case [CtFieldReadImpl]Person.T_VTOL_PILOT :
                                [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_PILOT_VTOL)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_PILOT_VTOL).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                                [CtBreakImpl]break;
                            [CtCaseImpl]case [CtFieldReadImpl]Person.T_NVEE_DRIVER :
                                [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_PILOT_NVEE)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_PILOT_NVEE).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                                [CtBreakImpl]break;
                            [CtCaseImpl]case [CtFieldReadImpl]Person.T_GVEE_DRIVER :
                                [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_PILOT_GVEE)) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_PILOT_GVEE).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL;
                                [CtBreakImpl]break;
                        }
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case [CtFieldReadImpl]PROTOMECH :
                    [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtBinaryOperatorImpl][CtLiteralImpl]2 * [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_GUN_PROTO) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_GUN_PROTO).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL);
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]case [CtFieldReadImpl]NAVAL :
                    [CtBlockImpl]{
                        [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]person.getPrimaryRole()) {
                            [CtCaseImpl]case [CtFieldReadImpl]Person.T_SPACE_CREW :
                                [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtBinaryOperatorImpl][CtLiteralImpl]2 * [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_TECH_VESSEL) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_TECH_VESSEL).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL);
                                [CtBreakImpl]break;
                            [CtCaseImpl]case [CtFieldReadImpl]Person.T_SPACE_GUNNER :
                                [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtBinaryOperatorImpl][CtLiteralImpl]2 * [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_GUN_SPACE) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_GUN_SPACE).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL);
                                [CtBreakImpl]break;
                            [CtCaseImpl]case [CtFieldReadImpl]Person.T_SPACE_PILOT :
                                [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtBinaryOperatorImpl][CtLiteralImpl]2 * [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_PILOT_SPACE) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_PILOT_SPACE).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL);
                                [CtBreakImpl]break;
                            [CtCaseImpl]case [CtFieldReadImpl]Person.T_NAVIGATOR :
                                [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtBinaryOperatorImpl][CtLiteralImpl]2 * [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]person.hasSkill([CtTypeAccessImpl]SkillType.S_NAV) ? [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getSkill([CtTypeAccessImpl]SkillType.S_NAV).getFinalSkillValue() : [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL);
                                [CtBreakImpl]break;
                        }
                        [CtBreakImpl]break;
                    }
                [CtCaseImpl]default :
                    [CtBlockImpl]{
                        [CtBreakImpl]break;
                    }
            }
            [CtIfImpl][CtCommentImpl]// Higher rated units are more likely to have Bloodnamed
            if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useDragoonRating()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.rating.IUnitRating rating = [CtInvocationImpl]getUnitRating();
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]bloodnameTarget += [CtBinaryOperatorImpl][CtFieldReadImpl]mekhq.campaign.rating.IUnitRating.DRAGOON_C - [CtConditionalImpl]([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUnitRatingMethod().equals([CtTypeAccessImpl]mekhq.campaign.rating.UnitRatingMethod.FLD_MAN_MERCS_REV) ? [CtInvocationImpl][CtVariableReadImpl]rating.getUnitRatingAsInteger() : [CtInvocationImpl][CtVariableReadImpl]rating.getModifier());
            }
            [CtLocalVariableImpl][CtCommentImpl]// Reavings diminish the number of available Bloodrights in later eras
            [CtTypeReferenceImpl]int year = [CtInvocationImpl]getGameYear();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]year <= [CtLiteralImpl]2950) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]bloodnameTarget--;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]year > [CtLiteralImpl]3055) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]bloodnameTarget++;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]year > [CtLiteralImpl]3065) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]bloodnameTarget++;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]year > [CtLiteralImpl]3080) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]bloodnameTarget++;
            }
            [CtOperatorAssignmentImpl][CtCommentImpl]// Officers have better chance; no penalty for non-officer
            [CtVariableWriteImpl]bloodnameTarget += [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]ranks.getOfficerCut() - [CtInvocationImpl][CtVariableReadImpl]person.getRankNumeric());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ignoreDice || [CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2) >= [CtVariableReadImpl]bloodnameTarget)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.personnel.enums.Phenotype phenotype = [CtInvocationImpl][CtVariableReadImpl]person.getPhenotype();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]phenotype == [CtFieldReadImpl]mekhq.campaign.personnel.enums.Phenotype.NONE) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]phenotype = [CtFieldReadImpl]mekhq.campaign.personnel.enums.Phenotype.GENERAL;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Bloodname bloodname = [CtInvocationImpl][CtTypeAccessImpl]Bloodname.randomBloodname([CtInvocationImpl][CtConditionalImpl]([CtInvocationImpl][CtInvocationImpl]getFaction().isClan() ? [CtInvocationImpl]getFaction() : [CtInvocationImpl][CtVariableReadImpl]person.getOriginFaction()).getShortName(), [CtVariableReadImpl]phenotype, [CtInvocationImpl]getGameYear());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]bloodname != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]person.setBloodname([CtInvocationImpl][CtVariableReadImpl]bloodname.getName());
                [CtInvocationImpl]personUpdated([CtVariableReadImpl]person);
            }
        }
    }

    [CtMethodImpl][CtCommentImpl]// endregion Bloodnames
    [CtCommentImpl]// region Other Personnel Methods
    [CtJavaDocImpl]/**
     * Imports a {@link Person} into a campaign.
     *
     * @param p
     * 		A {@link Person} to import into the campaign.
     */
    public [CtTypeReferenceImpl]void importPerson([CtParameterImpl][CtTypeReferenceImpl]Person p) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]personnel.put([CtInvocationImpl][CtVariableReadImpl]p.getId(), [CtVariableReadImpl]p);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PersonNewEvent([CtVariableReadImpl]p));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Person getPerson([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]personnel.get([CtVariableReadImpl]id);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]Person> getPersonnel() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]personnel.values();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Provides a filtered list of personnel including only active Persons.
     *
     * @return List<Person>
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> getActivePersonnel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> activePersonnel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isActive()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]activePersonnel.add([CtVariableReadImpl]p);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]activePersonnel;
    }

    [CtMethodImpl][CtCommentImpl]// endregion Other Personnel Methods
    [CtCommentImpl]// region Personnel Selectors and Generators
    [CtJavaDocImpl]/**
     * Gets the {@link AbstractFactionSelector} to use with this campaign.
     *
     * @return An {@link AbstractFactionSelector} to use when selecting a {@link Faction}.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.universe.AbstractFactionSelector getFactionSelector() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().randomizeOrigin()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.RangedFactionSelector selector = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.RangedFactionSelector([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getOriginSearchRadius());
            [CtInvocationImpl][CtVariableReadImpl]selector.setDistanceScale([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getOriginDistanceScale());
            [CtReturnImpl]return [CtVariableReadImpl]selector;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.DefaultFactionSelector();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the {@link AbstractPlanetSelector} to use with this campaign.
     *
     * @return An {@link AbstractPlanetSelector} to use when selecting a {@link Planet}.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.universe.AbstractPlanetSelector getPlanetSelector() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().randomizeOrigin()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.RangedPlanetSelector selector = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.RangedPlanetSelector([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getOriginSearchRadius(), [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().isOriginExtraRandom());
            [CtInvocationImpl][CtVariableReadImpl]selector.setDistanceScale([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getOriginDistanceScale());
            [CtReturnImpl]return [CtVariableReadImpl]selector;
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.universe.DefaultPlanetSelector();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the {@link AbstractPersonnelGenerator} to use with this campaign.
     *
     * @param factionSelector
     * 		The {@link AbstractFactionSelector} to use when choosing a {@link Faction}.
     * @param planetSelector
     * 		The {@link AbstractPlanetSelector} to use when choosing a {@link Planet}.
     * @return An {@link AbstractPersonnelGenerator} to use when creating new personnel.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.personnel.generator.AbstractPersonnelGenerator getPersonnelGenerator([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.AbstractFactionSelector factionSelector, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.AbstractPlanetSelector planetSelector) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.personnel.generator.DefaultPersonnelGenerator generator = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.personnel.generator.DefaultPersonnelGenerator([CtVariableReadImpl]factionSelector, [CtVariableReadImpl]planetSelector);
        [CtInvocationImpl][CtVariableReadImpl]generator.setNameGenerator([CtInvocationImpl][CtTypeAccessImpl]megamek.client.generator.RandomNameGenerator.getInstance());
        [CtInvocationImpl][CtVariableReadImpl]generator.setSkillPreferences([CtInvocationImpl]getRandomSkillPreferences());
        [CtReturnImpl]return [CtVariableReadImpl]generator;
    }

    [CtMethodImpl][CtCommentImpl]// endregion Personnel Selectors and Generators
    [CtCommentImpl]// endregion Personnel
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> getPatients() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> patients = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.needsFixing() || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useAdvancedMedical() && [CtInvocationImpl][CtVariableReadImpl]p.hasInjuries([CtLiteralImpl]true)) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isActive())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]patients.add([CtVariableReadImpl]p);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]patients;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> getServiceableUnits() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> service = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.isAvailable() && [CtInvocationImpl][CtVariableReadImpl]u.isServiceable()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]service.add([CtVariableReadImpl]u);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]service;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addPart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p, [CtParameterImpl][CtTypeReferenceImpl]int transitDays) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getUnit() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getUnit() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.unit.TestUnit)) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// if this is a test unit, then we won't add the part, so there
            return;
        }
        [CtInvocationImpl][CtVariableReadImpl]p.setDaysToArrival([CtVariableReadImpl]transitDays);
        [CtInvocationImpl][CtVariableReadImpl]p.setBrandNew([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// need to add ID here in case post-processing part stuff needs it
        [CtCommentImpl]// we will set the id back one if we don't end up adding this part
        [CtTypeReferenceImpl]int id = [CtBinaryOperatorImpl][CtFieldReadImpl]lastPartId + [CtLiteralImpl]1;
        [CtInvocationImpl][CtVariableReadImpl]p.setId([CtVariableReadImpl]id);
        [CtInvocationImpl][CtCommentImpl]// be careful in using this next line
        [CtVariableReadImpl]p.postProcessCampaignAddition();
        [CtIfImpl][CtCommentImpl]// don't add missing parts if they don't have units or units with not id
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtInvocationImpl][CtVariableReadImpl]p.getUnit()) || [CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtInvocationImpl][CtVariableReadImpl]p.getUnitId()))) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]p.setId([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtInvocationImpl][CtVariableReadImpl]p.getUnit()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.hasParentPart())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.isReservedForRefit())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.isReservedForReplacement())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part spare = [CtInvocationImpl]checkForExistingSparePart([CtVariableReadImpl]p);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]spare) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spare instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (spare)).setAmount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (spare)).getAmount() + [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (p)).getAmount());
                        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartChangedEvent([CtVariableReadImpl]spare));
                        [CtInvocationImpl][CtVariableReadImpl]p.setId([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                        [CtReturnImpl]return;
                    }
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spare instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (spare)).changeShots([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (p)).getShots());
                        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartChangedEvent([CtVariableReadImpl]spare));
                        [CtInvocationImpl][CtVariableReadImpl]p.setId([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                        [CtReturnImpl]return;
                    }
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]spare.incrementQuantity();
                    [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartChangedEvent([CtVariableReadImpl]spare));
                    [CtInvocationImpl][CtVariableReadImpl]p.setId([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                    [CtReturnImpl]return;
                }
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]parts.put([CtVariableReadImpl]id, [CtVariableReadImpl]p);
        [CtAssignmentImpl][CtFieldWriteImpl]lastPartId = [CtVariableReadImpl]id;
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartNewEvent([CtVariableReadImpl]p));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is similar to addPart, but we just check to see if this part can be added to an existing part, without actually
     * adding it to the campaign (because its already there). Should be called up when a part goes from 1 daysToArrival to
     * zero.
     *
     * @param p
     */
    public [CtTypeReferenceImpl]void arrivePart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]p.getUnit()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]p.setDaysToArrival([CtLiteralImpl]0);
        [CtInvocationImpl]addReport([CtInvocationImpl][CtVariableReadImpl]p.getArrivalReport());
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part spare = [CtConditionalImpl]([CtInvocationImpl][CtVariableReadImpl]p.isReservedForRefit()) ? [CtLiteralImpl]null : [CtInvocationImpl]checkForExistingSparePart([CtVariableReadImpl]p);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]spare) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int quantity = [CtInvocationImpl][CtVariableReadImpl]p.getQuantity();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spare instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                    [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]quantity > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (spare)).setAmount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (spare)).getAmount() + [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (p)).getAmount());
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]quantity--;
                    } 
                    [CtInvocationImpl]removePart([CtVariableReadImpl]p);
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spare instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
                    [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]quantity > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (spare)).changeShots([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (p)).getShots());
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]quantity--;
                    } 
                    [CtInvocationImpl]removePart([CtVariableReadImpl]p);
                }
            } else [CtBlockImpl]{
                [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]quantity > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]spare.incrementQuantity();
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]quantity--;
                } 
                [CtInvocationImpl]removePart([CtVariableReadImpl]p);
            }
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartArrivedEvent([CtVariableReadImpl]spare));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartArrivedEvent([CtVariableReadImpl]p));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Imports a collection of parts into the campaign.
     *
     * @param newParts
     * 		The collection of {@link Part} instances
     * 		to import into the campaign.
     */
    public [CtTypeReferenceImpl]void importParts([CtParameterImpl][CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]mekhq.campaign.parts.Part> newParts) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p : [CtVariableReadImpl]newParts) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]p.setCampaign([CtThisAccessImpl]this);
            [CtInvocationImpl]addPartWithoutId([CtVariableReadImpl]p);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p : [CtVariableReadImpl]newParts) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]p.fixupPartReferences([CtFieldReadImpl]parts);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addPartWithoutId([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart) && [CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtInvocationImpl][CtVariableReadImpl]p.getUnitId())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// we shouldn't have spare missing parts. I think their existence is
            [CtCommentImpl]// a relic.
            return;
        }
        [CtAssignmentImpl][CtCommentImpl]// Update the lastPartId we've seen to avoid overwriting a part,
        [CtCommentImpl]// which may occur if a replacement ID is assigned
        [CtFieldWriteImpl]lastPartId = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtFieldReadImpl]lastPartId, [CtInvocationImpl][CtVariableReadImpl]p.getId());
        [CtLocalVariableImpl][CtCommentImpl]// go ahead and check for existing parts because some version weren't
        [CtCommentImpl]// properly collecting parts
        [CtTypeReferenceImpl]mekhq.campaign.parts.Part mergedWith = [CtLiteralImpl]null;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart)) && [CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtInvocationImpl][CtVariableReadImpl]p.getUnitId())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.isReservedForRefit())) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.isReservedForReplacement())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part spare = [CtInvocationImpl]checkForExistingSparePart([CtVariableReadImpl]p);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]spare) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spare instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (spare)).setAmount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (spare)).getAmount() + [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (p)).getAmount());
                        [CtAssignmentImpl][CtVariableWriteImpl]mergedWith = [CtVariableReadImpl]spare;
                    }
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]spare instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (spare)).changeShots([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (p)).getShots());
                        [CtAssignmentImpl][CtVariableWriteImpl]mergedWith = [CtVariableReadImpl]spare;
                    }
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]spare.incrementQuantity();
                    [CtAssignmentImpl][CtVariableWriteImpl]mergedWith = [CtVariableReadImpl]spare;
                }
            }
        }
        [CtIfImpl][CtCommentImpl]// If we weren't merged we are being added
        if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]mergedWith) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]parts.put([CtInvocationImpl][CtVariableReadImpl]p.getId(), [CtVariableReadImpl]p);
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartNewEvent([CtVariableReadImpl]p));
        } else [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Go through each unit and its refits to see if the new armor should be updated
            [CtCommentImpl]// CAW: I believe all other parts on a refit have a unit assigned to them.
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]mergedWith instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Refit r = [CtInvocationImpl][CtVariableReadImpl]u.getRefit();
                    [CtIfImpl][CtCommentImpl]// If there is a refit and this part matches the new armor, update the armor entry
                    if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]r) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]r.getNewArmorSupplies() == [CtVariableReadImpl]p) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().info([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s (%d) was merged with %s (%d) used in a refit for %s", [CtInvocationImpl][CtVariableReadImpl]p.getName(), [CtInvocationImpl][CtVariableReadImpl]p.getId(), [CtInvocationImpl][CtVariableReadImpl]mergedWith.getName(), [CtInvocationImpl][CtVariableReadImpl]mergedWith.getId(), [CtInvocationImpl][CtVariableReadImpl]u.getName()));
                            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor mergedArmor = [CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (mergedWith));
                            [CtInvocationImpl][CtVariableReadImpl]r.setNewArmorSupplies([CtVariableReadImpl]mergedArmor);
                        }
                    }
                }
            }
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartRemovedEvent([CtVariableReadImpl]p));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return an <code>ArrayList</code> of SupportTeams in the campaign
     */
    public [CtTypeReferenceImpl]java.util.Collection<[CtTypeReferenceImpl]mekhq.campaign.parts.Part> getParts() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]parts.values();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]int getQuantity([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (p)).getAmount();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (p)).getShots();
        }
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getUnit() != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getUnitId() != [CtLiteralImpl]null) ? [CtLiteralImpl]1 : [CtInvocationImpl][CtVariableReadImpl]p.getQuantity();
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse getPartInUse([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// SI isn't a proper "part"
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.StructuralIntegrity) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl][CtCommentImpl]// Makes no sense buying those separately from the chasis
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) (p)).getType() != [CtLiteralImpl]null)) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) (p)).getType().hasFlag([CtTypeAccessImpl]MiscType.F_CHASSIS_MODIFICATION)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl][CtCommentImpl]// Replace a "missing" part with a corresponding "new" one.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart) (p)).getNewPart();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse result = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse([CtVariableReadImpl]p);
        [CtReturnImpl]return [CtConditionalImpl][CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]result.getPartToBuy() ? [CtVariableReadImpl]result : [CtLiteralImpl]null;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void updatePartInUseData([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse piu, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getUnit() != [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getUnitId() != [CtLiteralImpl]null)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]piu.setUseCount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]piu.getUseCount() + [CtInvocationImpl]getQuantity([CtVariableReadImpl]p));
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isPresent()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]piu.setStoreCount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]piu.getStoreCount() + [CtInvocationImpl]getQuantity([CtVariableReadImpl]p));
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]piu.setTransferCount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]piu.getTransferCount() + [CtInvocationImpl]getQuantity([CtVariableReadImpl]p));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Update the piu with the current campaign data
     */
    public [CtTypeReferenceImpl]void updatePartInUse([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse piu) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]piu.setUseCount([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]piu.setStoreCount([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]piu.setTransferCount([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]piu.setPlannedCount([CtLiteralImpl]0);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p : [CtInvocationImpl]getParts()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse newPiu = [CtInvocationImpl]getPartInUse([CtVariableReadImpl]p);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]piu.equals([CtVariableReadImpl]newPiu)) [CtBlockImpl]{
                [CtInvocationImpl]updatePartInUseData([CtVariableReadImpl]piu, [CtVariableReadImpl]p);
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork maybePart : [CtInvocationImpl][CtFieldReadImpl]shoppingList.getPartList()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse newPiu = [CtInvocationImpl]getPartInUse([CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Part) (maybePart)));
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]piu.equals([CtVariableReadImpl]newPiu)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]piu.setPlannedCount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]piu.getPlannedCount() + [CtBinaryOperatorImpl]([CtInvocationImpl]getQuantity([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]maybePart instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart) (maybePart)).getNewPart() : [CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Part) (maybePart))) * [CtInvocationImpl][CtVariableReadImpl]maybePart.getQuantity()));
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse> getPartsInUse() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// java.util.Set doesn't supply a get(Object) method, so we have to use a java.util.Map
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse, [CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse> inUse = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p : [CtInvocationImpl]getParts()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse piu = [CtInvocationImpl]getPartInUse([CtVariableReadImpl]p);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]piu) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]inUse.containsKey([CtVariableReadImpl]piu)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]piu = [CtInvocationImpl][CtVariableReadImpl]inUse.get([CtVariableReadImpl]piu);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]inUse.put([CtVariableReadImpl]piu, [CtVariableReadImpl]piu);
            }
            [CtInvocationImpl]updatePartInUseData([CtVariableReadImpl]piu, [CtVariableReadImpl]p);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork maybePart : [CtInvocationImpl][CtFieldReadImpl]shoppingList.getPartList()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]maybePart instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.PartInUse piu = [CtInvocationImpl]getPartInUse([CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Part) (maybePart)));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]piu) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]inUse.containsKey([CtVariableReadImpl]piu)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]piu = [CtInvocationImpl][CtVariableReadImpl]inUse.get([CtVariableReadImpl]piu);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]inUse.put([CtVariableReadImpl]piu, [CtVariableReadImpl]piu);
            }
            [CtInvocationImpl][CtVariableReadImpl]piu.setPlannedCount([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]piu.getPlannedCount() + [CtBinaryOperatorImpl]([CtInvocationImpl]getQuantity([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]maybePart instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart ? [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart) (maybePart)).getNewPart() : [CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Part) (maybePart))) * [CtInvocationImpl][CtVariableReadImpl]maybePart.getQuantity()));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]inUse.keySet();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.parts.Part getPart([CtParameterImpl][CtTypeReferenceImpl]int id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]parts.get([CtVariableReadImpl]id);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.force.Force getForce([CtParameterImpl][CtTypeReferenceImpl]int id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtVariableReadImpl]id);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getCurrentReport() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]currentReport;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setCurrentReportHTML([CtParameterImpl][CtTypeReferenceImpl]java.lang.String html) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]currentReportHTML = [CtVariableReadImpl]html;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCurrentReportHTML() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]currentReportHTML;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setNewReports([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> reports) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]newReports = [CtVariableReadImpl]reports;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> fetchAndClearNewReports() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> oldReports = [CtFieldReadImpl]newReports;
        [CtInvocationImpl]setNewReports([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
        [CtReturnImpl]return [CtVariableReadImpl]oldReports;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Finds the active person in a particular role with the highest level in a
     * given, with an optional secondary skill to break ties.
     *
     * @param role
     * 		One of the Person.T_* constants
     * @param primary
     * 		The skill to use for comparison.
     * @param secondary
     * 		If not null and there is more than one person tied for the most
     * 		the highest, preference will be given to the one with a higher
     * 		level in the secondary skill.
     * @return The admin in the designated role with the most experience.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Person findBestInRole([CtParameterImpl][CtTypeReferenceImpl]int role, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String primary, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String secondary) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int highest = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Person retVal = [CtLiteralImpl]null;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtVariableReadImpl]role) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getSecondaryRole() == [CtVariableReadImpl]role)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getSkill([CtVariableReadImpl]primary) != [CtLiteralImpl]null)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getSkill([CtVariableReadImpl]primary).getLevel() > [CtVariableReadImpl]highest) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]retVal = [CtVariableReadImpl]p;
                    [CtAssignmentImpl][CtVariableWriteImpl]highest = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getSkill([CtVariableReadImpl]primary).getLevel();
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]secondary != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getSkill([CtVariableReadImpl]primary).getLevel() == [CtVariableReadImpl]highest)) && [CtBinaryOperatorImpl][CtCommentImpl]/* If the skill level of the current person is the same as the previous highest,
                select the current instead under the following conditions:
                 */
                ([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]retVal == [CtLiteralImpl]null)[CtCommentImpl]// None has been selected yet (current has level 0)
                 || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]retVal.getSkill([CtVariableReadImpl]secondary) == [CtLiteralImpl]null))[CtCommentImpl]// Previous selection does not have secondary
                 || [CtBinaryOperatorImpl][CtCommentImpl]// skill
                ([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getSkill([CtVariableReadImpl]secondary) != [CtLiteralImpl]null)[CtCommentImpl]// Current has secondary skill and it is higher than the
                 && [CtBinaryOperatorImpl][CtCommentImpl]// previous.
                ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getSkill([CtVariableReadImpl]secondary).getLevel() > [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]retVal.getSkill([CtVariableReadImpl]secondary).getLevel())))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]retVal = [CtVariableReadImpl]p;
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]retVal;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Person findBestInRole([CtParameterImpl][CtTypeReferenceImpl]int role, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String skill) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]findBestInRole([CtVariableReadImpl]role, [CtVariableReadImpl]skill, [CtLiteralImpl]null);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return The list of all active {@link Person}s who qualify as technicians ({@link Person#isTech()}));
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> getTechs() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getTechs([CtLiteralImpl]false, [CtLiteralImpl]null, [CtLiteralImpl]true, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> getTechs([CtParameterImpl][CtTypeReferenceImpl]boolean noZeroMinute) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getTechs([CtVariableReadImpl]noZeroMinute, [CtLiteralImpl]null, [CtLiteralImpl]true, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns a list of active technicians.
     *
     * @param noZeroMinute
     * 		If TRUE, then techs with no time remaining will be excluded from
     * 		the list.
     * @param firstTechId
     * 		The ID of the tech that should appear first in the list (assuming
     * 		active and satisfies the noZeroMinute argument)
     * @param sorted
     * 		If TRUE, then return the list sorted from worst to best
     * @param eliteFirst
     * 		If TRUE and sorted also TRUE, then return the list sorted from
     * 		best to worst
     * @return The list of active {@link Person}s who qualify as technicians
    ({@link Person#isTech()}).
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> getTechs([CtParameterImpl][CtTypeReferenceImpl]boolean noZeroMinute, [CtParameterImpl][CtTypeReferenceImpl]java.util.UUID firstTechId, [CtParameterImpl][CtTypeReferenceImpl]boolean sorted, [CtParameterImpl][CtTypeReferenceImpl]boolean eliteFirst) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> techs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtCommentImpl]// Get the first tech.
        [CtTypeReferenceImpl]Person firstTech = [CtInvocationImpl]getPerson([CtVariableReadImpl]firstTechId);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]firstTech != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]firstTech.isTech()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]firstTech.getStatus().isActive()) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtVariableReadImpl]noZeroMinute) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]firstTech.getMinutesLeft() > [CtLiteralImpl]0))) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]techs.add([CtVariableReadImpl]firstTech);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.isTech() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.equals([CtVariableReadImpl]firstTech))) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtVariableReadImpl]noZeroMinute) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getMinutesLeft() > [CtLiteralImpl]0))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]techs.add([CtVariableReadImpl]p);
            }
        }
        [CtForEachImpl][CtCommentImpl]// also need to loop through and collect engineers on self-crewed vessels
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]u.isSelfCrewed() && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]u.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Infantry))) && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]u.getEngineer())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]techs.add([CtInvocationImpl][CtVariableReadImpl]u.getEngineer());
            }
        }
        [CtIfImpl][CtCommentImpl]// Return the tech collection sorted worst to best
        [CtCommentImpl]// Reverse the sort if we've been asked for best to worst
        if ([CtVariableReadImpl]sorted) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// First order by the amount of time the person has remaining, based on the sorting order
            [CtCommentImpl]// comparison changes because locations that use elite first will want the person with
            [CtCommentImpl]// the most remaining time at the top of the list while locations that don't will want it
            [CtCommentImpl]// at the bottom of the list
            if ([CtVariableReadImpl]eliteFirst) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// We want the highest amount of remaining time at the top of the list, as that
                [CtCommentImpl]// makes it easy to compare between the two
                [CtVariableReadImpl]techs.sort([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparingInt([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Person::getMinutesLeft));
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Otherwise, we want the highest amount of time being at the bottom of the list
                [CtVariableReadImpl]techs.sort([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparingInt([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Person::getMinutesLeft).reversed());
            }
            [CtInvocationImpl][CtCommentImpl]// Then sort by the skill level, which puts Elite personnel first or last dependant on
            [CtCommentImpl]// the eliteFirst value
            [CtVariableReadImpl]techs.sort([CtLambdaImpl]([CtParameterImpl] person1,[CtParameterImpl] person2) -> [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// default to 0, which means they're equal
                [CtTypeReferenceImpl]int retVal = [CtLiteralImpl]0;
                [CtLocalVariableImpl][CtCommentImpl]// Set up booleans to know if the tech is secondary only
                [CtCommentImpl]// this is to get the skill from getExperienceLevel(boolean) properly
                [CtTypeReferenceImpl]boolean p1Secondary = [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]person1.isTechPrimary()) && [CtInvocationImpl][CtVariableReadImpl]person1.isTechSecondary();
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean p2Secondary = [CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]person2.isTechPrimary()) && [CtInvocationImpl][CtVariableReadImpl]person2.isTechSecondary();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person1.getExperienceLevel([CtVariableReadImpl]p1Secondary) > [CtInvocationImpl][CtVariableReadImpl]person2.getExperienceLevel([CtVariableReadImpl]p2Secondary)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// Person 1 is better than Person 2.
                    [CtVariableWriteImpl]retVal = [CtLiteralImpl]1;
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person1.getExperienceLevel([CtVariableReadImpl]p1Secondary) < [CtInvocationImpl][CtVariableReadImpl]person2.getExperienceLevel([CtVariableReadImpl]p2Secondary)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// Person 2 is better than Person 1
                    [CtVariableWriteImpl]retVal = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
                }
                [CtReturnImpl][CtCommentImpl]// Return, swapping the value if we're looking to have Elites ordered first
                return [CtConditionalImpl][CtVariableReadImpl]eliteFirst ? [CtUnaryOperatorImpl]-[CtVariableReadImpl]retVal : [CtVariableReadImpl]retVal;
            });
        }
        [CtReturnImpl]return [CtVariableReadImpl]techs;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a list of all techs of a specific role type
     *
     * @param roleType
     * 		The filter role type
     * @return Collection of all techs that match the given tech role
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> getTechsByRole([CtParameterImpl][CtTypeReferenceImpl]int roleType) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> techs = [CtInvocationImpl]getTechs([CtLiteralImpl]false, [CtLiteralImpl]null, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> retval = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person tech : [CtVariableReadImpl]techs) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getPrimaryRole() == [CtVariableReadImpl]roleType) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getSecondaryRole() == [CtVariableReadImpl]roleType)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]retval.add([CtVariableReadImpl]tech);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]retval;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> getAdmins() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> admins = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isAdmin()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]admins.add([CtVariableReadImpl]p);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]admins;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isWorkingOnRefit([CtParameterImpl][CtTypeReferenceImpl]Person p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit = [CtInvocationImpl][CtInvocationImpl]getHangar().findUnit([CtLambdaImpl]([CtParameterImpl] u) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.isRefitting() && [CtInvocationImpl][CtTypeAccessImpl]Objects.equals([CtInvocationImpl][CtVariableReadImpl]p.getId(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getRefit().getTeamId()));
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtVariableReadImpl]unit != [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> getDoctors() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> docs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isDoctor()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]docs.add([CtVariableReadImpl]p);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]docs;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getPatientsFor([CtParameterImpl][CtTypeReferenceImpl]Person doctor) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int patients = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person person : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]person.getDoctorId()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getDoctorId().equals([CtInvocationImpl][CtVariableReadImpl]doctor.getId())) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]patients++;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]patients;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String healPerson([CtParameterImpl][CtTypeReferenceImpl]Person medWork, [CtParameterImpl][CtTypeReferenceImpl]Person doctor) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useAdvancedMedical()) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]"";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String report = [CtLiteralImpl]"";
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]doctor.getHyperlinkedFullTitle() + [CtLiteralImpl]" attempts to heal ") + [CtInvocationImpl][CtVariableReadImpl]medWork.getFullName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtInvocationImpl]getTargetFor([CtVariableReadImpl]medWork, [CtVariableReadImpl]doctor);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
        [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]report + [CtLiteralImpl]",  needs ") + [CtInvocationImpl][CtVariableReadImpl]target.getValueAsString()) + [CtLiteralImpl]" and rolls ") + [CtVariableReadImpl]roll) + [CtLiteralImpl]":";
        [CtLocalVariableImpl][CtTypeReferenceImpl]int xpGained = [CtLiteralImpl]0;
        [CtIfImpl][CtCommentImpl]// If we get a natural 2 that isn't an automatic success, reroll if Edge is available and in use.
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useSupportEdge() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]doctor.getOptions().booleanOption([CtTypeAccessImpl]PersonnelOptions.EDGE_MEDICAL)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]roll == [CtLiteralImpl]2) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]doctor.getCurrentEdge() > [CtLiteralImpl]0)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]doctor.setCurrentEdge([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]doctor.getCurrentEdge() - [CtLiteralImpl]1);
                [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]medWork.fail() + [CtLiteralImpl]"\n") + [CtInvocationImpl][CtVariableReadImpl]doctor.getHyperlinkedFullTitle()) + [CtLiteralImpl]" uses Edge to reroll:") + [CtLiteralImpl]" rolls ") + [CtVariableReadImpl]roll) + [CtLiteralImpl]":";
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]roll >= [CtInvocationImpl][CtVariableReadImpl]target.getValue()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtVariableReadImpl]report + [CtInvocationImpl][CtVariableReadImpl]medWork.succeed();
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtInvocationImpl][CtVariableReadImpl]medWork.getUnitId());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]u) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]u.resetPilotAndEntity();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]roll == [CtLiteralImpl]12) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS)) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]xpGained += [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getSuccessXP();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]doctor.setNTasks([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]doctor.getNTasks() + [CtLiteralImpl]1);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]doctor.getNTasks() >= [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getNTasksXP()) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]xpGained += [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getTaskXP();
                [CtInvocationImpl][CtVariableReadImpl]doctor.setNTasks([CtLiteralImpl]0);
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtVariableReadImpl]report + [CtInvocationImpl][CtVariableReadImpl]medWork.fail();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]roll == [CtLiteralImpl]2) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL)) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]xpGained += [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getMistakeXP();
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]xpGained > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]doctor.awardXP([CtVariableReadImpl]xpGained);
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]" (" + [CtVariableReadImpl]xpGained) + [CtLiteralImpl]"XP gained) ";
        }
        [CtInvocationImpl][CtVariableReadImpl]medWork.setDaysToWaitForHealing([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getHealingWaitingPeriod());
        [CtReturnImpl]return [CtVariableReadImpl]report;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.TargetRoll getTargetFor([CtParameterImpl][CtTypeReferenceImpl]Person medWork, [CtParameterImpl][CtTypeReferenceImpl]Person doctor) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Skill skill = [CtInvocationImpl][CtVariableReadImpl]doctor.getSkill([CtTypeAccessImpl]SkillType.S_DOCTOR);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]skill) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]doctor.getFullName() + [CtLiteralImpl]" isn't a doctor, he just plays one on TV.");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]medWork.getDoctorId() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]medWork.getDoctorId().equals([CtInvocationImpl][CtVariableReadImpl]doctor.getId()))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]medWork.getFullName() + [CtLiteralImpl]" is already being tended by another doctor");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]medWork.needsFixing()) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useAdvancedMedical() && [CtInvocationImpl][CtVariableReadImpl]medWork.needsAMFixing()))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]medWork.getFullName() + [CtLiteralImpl]" does not require healing.");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getPatientsFor([CtVariableReadImpl]doctor) > [CtLiteralImpl]25) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]doctor.getFullName() + [CtLiteralImpl]" already has 25 patients.");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtInvocationImpl][CtVariableReadImpl]skill.getFinalSkillValue(), [CtInvocationImpl][CtTypeAccessImpl]SkillType.getExperienceLevelName([CtInvocationImpl][CtVariableReadImpl]skill.getExperienceLevel()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getValue() == [CtFieldReadImpl]TargetRoll.IMPOSSIBLE) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]target;
        }
        [CtLocalVariableImpl][CtCommentImpl]// understaffed mods
        [CtTypeReferenceImpl]int helpMod = [CtInvocationImpl]getShorthandedMod([CtInvocationImpl]getMedicsPerDoctor(), [CtLiteralImpl]true);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]helpMod > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtVariableReadImpl]helpMod, [CtLiteralImpl]"shorthanded");
        }
        [CtInvocationImpl][CtVariableReadImpl]target.append([CtInvocationImpl][CtVariableReadImpl]medWork.getHealingMods());
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Person getLogisticsPerson() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int bestSkill = [CtUnaryOperatorImpl]-[CtLiteralImpl]1;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxAcquisitions = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getMaxAcquisitions();
        [CtLocalVariableImpl][CtTypeReferenceImpl]Person admin = [CtLiteralImpl]null;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String skill = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquisitionSkill();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]skill.equals([CtTypeAccessImpl]CampaignOptions.S_AUTO)) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]admin;
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]skill.equals([CtTypeAccessImpl]CampaignOptions.S_TECH)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().isAcquisitionSupportStaffOnly() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.hasSupportRole([CtLiteralImpl]false))) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]maxAcquisitions > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getAcquisitions() >= [CtVariableReadImpl]maxAcquisitions)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getBestTechSkill() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getBestTechSkill().getLevel() > [CtVariableReadImpl]bestSkill)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]admin = [CtVariableReadImpl]p;
                    [CtAssignmentImpl][CtVariableWriteImpl]bestSkill = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getBestTechSkill().getLevel();
                }
            }
        } else [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().isAcquisitionSupportStaffOnly() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.hasSupportRole([CtLiteralImpl]false))) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]maxAcquisitions > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getAcquisitions() >= [CtVariableReadImpl]maxAcquisitions)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.hasSkill([CtVariableReadImpl]skill) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getSkill([CtVariableReadImpl]skill).getLevel() > [CtVariableReadImpl]bestSkill)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]admin = [CtVariableReadImpl]p;
                    [CtAssignmentImpl][CtVariableWriteImpl]bestSkill = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getSkill([CtVariableReadImpl]skill).getLevel();
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]admin;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a list of applicable logistics personnel, or an empty list
     * if acquisitions automatically succeed.
     *
     * @return A {@see List} of personnel who can perform logistical actions.
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> getLogisticsPersonnel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String skill = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquisitionSkill();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]skill.equals([CtTypeAccessImpl]CampaignOptions.S_AUTO)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> logisticsPersonnel = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]int maxAcquisitions = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getMaxAcquisitions();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().isAcquisitionSupportStaffOnly() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.hasSupportRole([CtLiteralImpl]false))) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]maxAcquisitions > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getAcquisitions() >= [CtVariableReadImpl]maxAcquisitions)) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]skill.equals([CtTypeAccessImpl]CampaignOptions.S_TECH)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]p.getBestTechSkill()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]logisticsPersonnel.add([CtVariableReadImpl]p);
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.hasSkill([CtVariableReadImpl]skill)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]logisticsPersonnel.add([CtVariableReadImpl]p);
                }
            }
            [CtInvocationImpl][CtCommentImpl]// Sort by their skill level, descending.
            [CtVariableReadImpl]logisticsPersonnel.sort([CtLambdaImpl]([CtParameterImpl] a,[CtParameterImpl] b) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]skill.equals([CtVariableReadImpl]CampaignOptions.S_TECH)) [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.compare([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.getBestTechSkill().getLevel(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]a.getBestTechSkill().getLevel());
                } else [CtBlockImpl]{
                    [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Integer.compare([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]b.getSkill([CtVariableReadImpl]skill).getLevel(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]a.getSkill([CtVariableReadImpl]skill).getLevel());
                }
            });
            [CtReturnImpl]return [CtVariableReadImpl]logisticsPersonnel;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     * This is the main function for getting stuff (parts, units, etc.) All non-GM
     * acquisition should go through this function to ensure the campaign rules for
     * acquisition are followed.
     *
     * @param sList
     * 		- A <code>ShoppingList</code> object including items that need
     * 		to be purchased
     * @return A <code>ShoppingList</code> object that includes all items that were
    not successfully acquired
     */
    public [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList goShopping([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList sList) [CtBlockImpl]{
        [CtForEachImpl][CtCommentImpl]// loop through shopping items and decrement days to wait
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork shoppingItem : [CtInvocationImpl][CtVariableReadImpl]sList.getAllShoppingItems()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]shoppingItem.decrementDaysToWait();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquisitionSkill().equals([CtTypeAccessImpl]CampaignOptions.S_AUTO)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]goShoppingAutomatically([CtVariableReadImpl]sList);
        } else [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usesPlanetaryAcquisition()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]goShoppingStandard([CtVariableReadImpl]sList);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]goShoppingByPlanet([CtVariableReadImpl]sList);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shops for items on the {@link ShoppingList}, where each acquisition
     * automatically succeeds.
     *
     * @param sList
     * 		The shopping list to use when shopping.
     * @return The new shopping list containing the items that were not
    acquired.
     */
    private [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList goShoppingAutomatically([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList sList) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork> currentList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]sList.getAllShoppingItems());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork> remainingItems = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]currentList.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork shoppingItem : [CtVariableReadImpl]currentList) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]shoppingItem.getDaysToWait() <= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtWhileImpl]while ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]shoppingItem.getQuantity() > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]acquireEquipment([CtVariableReadImpl]shoppingItem, [CtLiteralImpl]null)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]shoppingItem.resetDaysToWait();
                        [CtBreakImpl]break;
                    }
                } 
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]shoppingItem.getQuantity() > [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]shoppingItem.getDaysToWait() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]remainingItems.add([CtVariableReadImpl]shoppingItem);
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList([CtVariableReadImpl]remainingItems);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shops for items on the {@link ShoppingList}, where each acquisition
     * is performed by available logistics personnel.
     *
     * @param sList
     * 		The shopping list to use when shopping.
     * @return The new shopping list containing the items that were not
    acquired.
     */
    private [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList goShoppingStandard([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList sList) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> logisticsPersonnel = [CtInvocationImpl]getLogisticsPersonnel();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]logisticsPersonnel.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtLiteralImpl]"Your force has no one capable of acquiring equipment.");
            [CtReturnImpl]return [CtVariableReadImpl]sList;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork> currentList = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]sList.getAllShoppingItems());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person person : [CtVariableReadImpl]logisticsPersonnel) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]currentList.isEmpty()) [CtBlockImpl]{
                [CtBreakImpl][CtCommentImpl]// Nothing left to shop for!
                break;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork> remainingItems = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]currentList.size());
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork shoppingItem : [CtVariableReadImpl]currentList) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]shoppingItem.getDaysToWait() <= [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtWhileImpl]while ([CtBinaryOperatorImpl][CtInvocationImpl]canAcquireParts([CtVariableReadImpl]person) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]shoppingItem.getQuantity() > [CtLiteralImpl]0)) [CtBlockImpl]{
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]acquireEquipment([CtVariableReadImpl]shoppingItem, [CtVariableReadImpl]person)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]shoppingItem.resetDaysToWait();
                            [CtBreakImpl]break;
                        }
                    } 
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]shoppingItem.getQuantity() > [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]shoppingItem.getDaysToWait() > [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]remainingItems.add([CtVariableReadImpl]shoppingItem);
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]currentList = [CtVariableReadImpl]remainingItems;
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList([CtVariableReadImpl]currentList);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Shops for items on the {@link ShoppingList}, where each acquisition
     * is attempted on nearby planets by available logistics personnel.
     *
     * @param sList
     * 		The shopping list to use when shopping.
     * @return The new shopping list containing the items that were not
    acquired.
     */
    private [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList goShoppingByPlanet([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList sList) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> logisticsPersonnel = [CtInvocationImpl]getLogisticsPersonnel();
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]logisticsPersonnel.isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtLiteralImpl]"Your force has no one capable of acquiring equipment.");
            [CtReturnImpl]return [CtVariableReadImpl]sList;
        }
        [CtLocalVariableImpl][CtCommentImpl]// we are shopping by planets, so more involved
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork> currentList = [CtInvocationImpl][CtVariableReadImpl]sList.getAllShoppingItems();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate currentDate = [CtInvocationImpl]getLocalDate();
        [CtLocalVariableImpl][CtCommentImpl]// a list of items than can be taken out of the search and put back on the
        [CtCommentImpl]// shopping list
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork> shelvedItems = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtCommentImpl]// find planets within a certain radius - the function will weed out dead planets
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem> systems = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getShoppingSystems([CtInvocationImpl]getCurrentSystem(), [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getMaxJumpsPlanetaryAcquisition(), [CtVariableReadImpl]currentDate);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person person : [CtVariableReadImpl]logisticsPersonnel) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]currentList.isEmpty()) [CtBlockImpl]{
                [CtBreakImpl][CtCommentImpl]// Nothing left to shop for!
                break;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String personTitle = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person.getHyperlinkedFullTitle() + [CtLiteralImpl]" ";
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem system : [CtVariableReadImpl]systems) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]currentList.isEmpty()) [CtBlockImpl]{
                    [CtBreakImpl][CtCommentImpl]// Nothing left to shop for!
                    break;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork> remainingItems = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
                [CtLocalVariableImpl][CtCommentImpl]// loop through shopping list. If its time to check, then check as appropriate. Items not
                [CtCommentImpl]// found get added to the remaining item list. Rotate through personnel
                [CtTypeReferenceImpl]boolean done = [CtLiteralImpl]false;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork shoppingItem : [CtVariableReadImpl]currentList) [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]canAcquireParts([CtVariableReadImpl]person)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]remainingItems.add([CtVariableReadImpl]shoppingItem);
                        [CtAssignmentImpl][CtVariableWriteImpl]done = [CtLiteralImpl]true;
                        [CtContinueImpl]continue;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]shoppingItem.getDaysToWait() <= [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtIfImpl]if ([CtInvocationImpl]findContactForAcquisition([CtVariableReadImpl]shoppingItem, [CtVariableReadImpl]person, [CtVariableReadImpl]system)) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]int transitTime = [CtInvocationImpl]calculatePartTransitTime([CtVariableReadImpl]system);
                            [CtLocalVariableImpl][CtTypeReferenceImpl]int totalQuantity = [CtLiteralImpl]0;
                            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]shoppingItem.getQuantity() > [CtLiteralImpl]0) && [CtInvocationImpl]canAcquireParts([CtVariableReadImpl]person)) && [CtInvocationImpl]acquireEquipment([CtVariableReadImpl]shoppingItem, [CtVariableReadImpl]person, [CtVariableReadImpl]system, [CtVariableReadImpl]transitTime)) [CtBlockImpl]{
                                [CtUnaryOperatorImpl][CtVariableWriteImpl]totalQuantity++;
                            } 
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]totalQuantity > [CtLiteralImpl]0) [CtBlockImpl]{
                                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]personTitle + [CtLiteralImpl]"<font color='green'><b> found ") + [CtInvocationImpl][CtVariableReadImpl]shoppingItem.getQuantityName([CtVariableReadImpl]totalQuantity)) + [CtLiteralImpl]" on ") + [CtInvocationImpl][CtVariableReadImpl]system.getPrintableName([CtVariableReadImpl]currentDate)) + [CtLiteralImpl]". Delivery in ") + [CtVariableReadImpl]transitTime) + [CtLiteralImpl]" days.</b></font>");
                            }
                        }
                    }
                    [CtIfImpl][CtCommentImpl]// if we didn't find everything on this planet, then add to the remaining list
                    if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]shoppingItem.getQuantity() > [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]shoppingItem.getDaysToWait() > [CtLiteralImpl]0)) [CtBlockImpl]{
                        [CtIfImpl][CtCommentImpl]// if we can't afford it, then don't keep searching for it on other planets
                        if ([CtUnaryOperatorImpl]![CtInvocationImpl]canPayFor([CtVariableReadImpl]shoppingItem)) [CtBlockImpl]{
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usePlanetAcquisitionVerboseReporting()) [CtBlockImpl]{
                                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='red'><b>You cannot afford to purchase another " + [CtInvocationImpl][CtVariableReadImpl]shoppingItem.getAcquisitionName()) + [CtLiteralImpl]"</b></font>");
                            }
                            [CtInvocationImpl][CtVariableReadImpl]shelvedItems.add([CtVariableReadImpl]shoppingItem);
                        } else [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]remainingItems.add([CtVariableReadImpl]shoppingItem);
                        }
                    }
                }
                [CtAssignmentImpl][CtCommentImpl]// we are done with this planet. replace our current list with the remaining items
                [CtVariableWriteImpl]currentList = [CtVariableReadImpl]remainingItems;
                [CtIfImpl]if ([CtVariableReadImpl]done) [CtBlockImpl]{
                    [CtBreakImpl]break;
                }
            }
        }
        [CtInvocationImpl][CtCommentImpl]// add shelved items back to the currentlist
        [CtVariableReadImpl]currentList.addAll([CtVariableReadImpl]shelvedItems);
        [CtForEachImpl][CtCommentImpl]// loop through and reset waiting time on all items on the remaining shopping
        [CtCommentImpl]// list if they have no waiting time left
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork shoppingItem : [CtVariableReadImpl]currentList) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]shoppingItem.getDaysToWait() <= [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]shoppingItem.resetDaysToWait();
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.market.ShoppingList([CtVariableReadImpl]currentList);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a value indicating if {@code person} can acquire parts.
     *
     * @param person
     * 		The {@link Person} to check if they have remaining
     * 		time to perform acquisitions.
     * @return True if {@code person} could acquire another part, otherwise false.
     */
    public [CtTypeReferenceImpl]boolean canAcquireParts([CtParameterImpl][CtAnnotationImpl]@megamek.common.annotations.Nullable
    [CtTypeReferenceImpl]Person person) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]person == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// CAW: in this case we're using automatic success
            [CtCommentImpl]// and the logistics person will be null.
            return [CtLiteralImpl]true;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int maxAcquisitions = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getMaxAcquisitions();
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]maxAcquisitions <= [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]person.getAcquisitions() < [CtVariableReadImpl]maxAcquisitions);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     * Checks whether the campaign can pay for a given <code>IAcquisitionWork</code> item. This will check
     * both whether the campaign is required to pay for a given type of acquisition by the options and
     * if so whether it has enough money to afford it.
     *
     * @param acquisition
     * 		- An <code>IAcquisitionWork<code> object
     * @return true if the campaign can pay for the acquisition; false if it cannot.
     */
    public [CtTypeReferenceImpl]boolean canPayFor([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork acquisition) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// SHOULD we check to see if this acquisition needs to be paid for
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.unit.UnitOrder) && [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().payForUnits()) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part) && [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().payForParts())) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// CAN the acquisition actually be paid for
            return [CtInvocationImpl][CtInvocationImpl]getFunds().isGreaterOrEqualThan([CtInvocationImpl][CtVariableReadImpl]acquisition.getBuyCost());
        }
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Make an acquisition roll for a given planet to see if you can identify a contact. Used for planetary based acquisition.
     *
     * @param acquisition
     * 		- The <code> IAcquisitionWork</code> being acquired.
     * @param person
     * 		- The <code>Person</code> object attempting to do the acquiring.  may be null if no one on the force has the skill or the user is using automatic acquisition.
     * @param system
     * 		- The <code>PlanetarySystem</code> object where the acquisition is being attempted. This may be null if the user is not using planetary acquisition.
     * @return true if your target roll succeeded.
     */
    public [CtTypeReferenceImpl]boolean findContactForAcquisition([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork acquisition, [CtParameterImpl][CtTypeReferenceImpl]Person person, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem system) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtInvocationImpl]getTargetForAcquisition([CtVariableReadImpl]acquisition, [CtVariableReadImpl]person, [CtLiteralImpl]false);
        [CtAssignmentImpl][CtVariableWriteImpl]target = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]system.getPrimaryPlanet().getAcquisitionMods([CtVariableReadImpl]target, [CtInvocationImpl]getLocalDate(), [CtInvocationImpl]getCampaignOptions(), [CtInvocationImpl]getFaction(), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]acquisition.getTechBase() == [CtFieldReadImpl]mekhq.campaign.parts.Part.T_CLAN);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getValue() == [CtFieldReadImpl]TargetRoll.IMPOSSIBLE) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usePlanetAcquisitionVerboseReporting()) [CtBlockImpl]{
                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='red'><b>Can't search for " + [CtInvocationImpl][CtVariableReadImpl]acquisition.getAcquisitionName()) + [CtLiteralImpl]" on ") + [CtInvocationImpl][CtVariableReadImpl]system.getPrintableName([CtInvocationImpl]getLocalDate())) + [CtLiteralImpl]" because:</b></font> ") + [CtInvocationImpl][CtVariableReadImpl]target.getDesc());
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2) < [CtInvocationImpl][CtVariableReadImpl]target.getValue()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// no contacts on this planet, move along
            if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usePlanetAcquisitionVerboseReporting()) [CtBlockImpl]{
                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='red'><b>No contacts available for " + [CtInvocationImpl][CtVariableReadImpl]acquisition.getAcquisitionName()) + [CtLiteralImpl]" on ") + [CtInvocationImpl][CtVariableReadImpl]system.getPrintableName([CtInvocationImpl]getLocalDate())) + [CtLiteralImpl]"</b></font>");
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        } else [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usePlanetAcquisitionVerboseReporting()) [CtBlockImpl]{
                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='green'>Possible contact for " + [CtInvocationImpl][CtVariableReadImpl]acquisition.getAcquisitionName()) + [CtLiteralImpl]" on ") + [CtInvocationImpl][CtVariableReadImpl]system.getPrintableName([CtInvocationImpl]getLocalDate())) + [CtLiteralImpl]"</font>");
            }
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     * Attempt to acquire a given <code>IAcquisitionWork</code> object.
     * This is the default method used by for non-planetary based acquisition.
     *
     * @param acquisition
     * 		- The <code> IAcquisitionWork</code> being acquired.
     * @param person
     * 		- The <code>Person</code> object attempting to do the acquiring.  may be null if no one on the force has the skill or the user is using automatic acquisition.
     * @return a boolean indicating whether the attempt to acquire equipment was successful.
     */
    public [CtTypeReferenceImpl]boolean acquireEquipment([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork acquisition, [CtParameterImpl][CtTypeReferenceImpl]Person person) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]acquireEquipment([CtVariableReadImpl]acquisition, [CtVariableReadImpl]person, [CtLiteralImpl]null, [CtUnaryOperatorImpl]-[CtLiteralImpl]1);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     * Attempt to acquire a given <code>IAcquisitionWork</code> object.
     *
     * @param acquisition
     * 		- The <code> IAcquisitionWork</code> being acquired.
     * @param person
     * 		- The <code>Person</code> object attempting to do the acquiring.  may be null if no one on the force has the skill or the user is using automatic acquisition.
     * @param system
     * 		- The <code>PlanetarySystem</code> object where the acquisition is being attempted. This may be null if the user is not using planetary acquisition.
     * @param transitDays
     * 		- The number of days that the part should take to be delivered. If this value is entered as -1, then this method will determine transit time based on the users campaign options.
     * @return a boolean indicating whether the attempt to acquire equipment was successful.
     */
    private [CtTypeReferenceImpl]boolean acquireEquipment([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork acquisition, [CtParameterImpl][CtTypeReferenceImpl]Person person, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem system, [CtParameterImpl][CtTypeReferenceImpl]int transitDays) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean found = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String report = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]person) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person.getHyperlinkedFullTitle() + [CtLiteralImpl]" ";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtInvocationImpl]getTargetForAcquisition([CtVariableReadImpl]acquisition, [CtVariableReadImpl]person, [CtLiteralImpl]false);
        [CtIfImpl][CtCommentImpl]// check on funds
        if ([CtUnaryOperatorImpl]![CtInvocationImpl]canPayFor([CtVariableReadImpl]acquisition)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtTypeAccessImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"Cannot afford this purchase");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]system) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]target = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]system.getPrimaryPlanet().getAcquisitionMods([CtVariableReadImpl]target, [CtInvocationImpl]getLocalDate(), [CtInvocationImpl]getCampaignOptions(), [CtInvocationImpl]getFaction(), [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]acquisition.getTechBase() == [CtFieldReadImpl]mekhq.campaign.parts.Part.T_CLAN);
        }
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtLiteralImpl]"attempts to find " + [CtInvocationImpl][CtVariableReadImpl]acquisition.getAcquisitionName();
        [CtIfImpl][CtCommentImpl]// if impossible then return
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getValue() == [CtFieldReadImpl]TargetRoll.IMPOSSIBLE) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]":<font color='red'><b> " + [CtInvocationImpl][CtVariableReadImpl]target.getDesc()) + [CtLiteralImpl]"</b></font>";
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usesPlanetaryAcquisition()) || [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usePlanetAcquisitionVerboseReporting()) [CtBlockImpl]{
                [CtInvocationImpl]addReport([CtVariableReadImpl]report);
            }
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtLiteralImpl]"  needs " + [CtInvocationImpl][CtVariableReadImpl]target.getValueAsString();
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]" and rolls " + [CtVariableReadImpl]roll) + [CtLiteralImpl]":";
        [CtIfImpl][CtCommentImpl]// Edge reroll, if applicable
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]roll < [CtInvocationImpl][CtVariableReadImpl]target.getValue()) && [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useSupportEdge()) && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]person)) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getOptions().booleanOption([CtTypeAccessImpl]PersonnelOptions.EDGE_ADMIN_ACQUIRE_FAIL)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]person.getCurrentEdge() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]person.setCurrentEdge([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person.getCurrentEdge() - [CtLiteralImpl]1);
            [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]" <b>failed!</b> but uses Edge to reroll...getting a " + [CtVariableReadImpl]roll) + [CtLiteralImpl]": ";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int mos = [CtBinaryOperatorImpl][CtVariableReadImpl]roll - [CtInvocationImpl][CtVariableReadImpl]target.getValue();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getValue() == [CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]mos = [CtBinaryOperatorImpl][CtVariableReadImpl]roll - [CtLiteralImpl]2;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int xpGained = [CtLiteralImpl]0;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]roll >= [CtInvocationImpl][CtVariableReadImpl]target.getValue()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]transitDays < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]transitDays = [CtInvocationImpl]calculatePartTransitTime([CtVariableReadImpl]mos);
            }
            [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtVariableReadImpl]report + [CtInvocationImpl][CtVariableReadImpl]acquisition.find([CtVariableReadImpl]transitDays);
            [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]person != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]roll == [CtLiteralImpl]12) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS)) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]xpGained += [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getSuccessXP();
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]person.setNTasks([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person.getNTasks() + [CtLiteralImpl]1);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person.getNTasks() >= [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getNTasksXP()) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]xpGained += [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getTaskXP();
                    [CtInvocationImpl][CtVariableReadImpl]person.setNTasks([CtLiteralImpl]0);
                }
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtVariableReadImpl]report + [CtInvocationImpl][CtVariableReadImpl]acquisition.failToFind();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]person != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtVariableReadImpl]roll == [CtLiteralImpl]2)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL)) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]xpGained += [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getMistakeXP();
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]person) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// The person should have their acquisitions incremented
            [CtVariableReadImpl]person.incrementAcquisition();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]xpGained > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]person.awardXP([CtVariableReadImpl]xpGained);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]" (" + [CtVariableReadImpl]xpGained) + [CtLiteralImpl]"XP gained) ";
            }
        }
        [CtIfImpl]if ([CtVariableReadImpl]found) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]acquisition.decrementQuantity();
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.AcquisitionEvent([CtVariableReadImpl]acquisition));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usesPlanetaryAcquisition()) || [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usePlanetAcquisitionVerboseReporting()) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtVariableReadImpl]report);
        }
        [CtReturnImpl]return [CtVariableReadImpl]found;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Performs work to either mothball or activate a unit.
     *
     * @param u
     * 		The unit to either work towards mothballing or activation.
     */
    public [CtTypeReferenceImpl]void workOnMothballingOrActivation([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]u.isMothballed()) [CtBlockImpl]{
            [CtInvocationImpl]activate([CtVariableReadImpl]u);
        } else [CtBlockImpl]{
            [CtInvocationImpl]mothball([CtVariableReadImpl]u);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Performs work to mothball a unit.
     *
     * @param u
     * 		The unit on which to perform mothball work.
     */
    public [CtTypeReferenceImpl]void mothball([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]u.isMothballed()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().warning([CtFieldReadImpl]mekhq.campaign.Campaign.class, [CtLiteralImpl]"mothball(Unit)", [CtLiteralImpl]"Unit is already mothballed, cannot mothball.");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Person tech = [CtInvocationImpl][CtVariableReadImpl]u.getTech();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]tech) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// uh-oh
            addReport([CtBinaryOperatorImpl][CtLiteralImpl]"No tech assigned to the mothballing of " + [CtInvocationImpl][CtVariableReadImpl]u.getHyperlinkedName());
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// don't allow overtime minutes for mothballing because its cheating
        [CtCommentImpl]// since you don't roll
        [CtTypeReferenceImpl]int minutes = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft(), [CtInvocationImpl][CtVariableReadImpl]u.getMothballTime());
        [CtIfImpl][CtCommentImpl]// check astech time
        if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]u.isSelfCrewed()) && [CtBinaryOperatorImpl]([CtFieldReadImpl]astechPoolMinutes < [CtBinaryOperatorImpl]([CtVariableReadImpl]minutes * [CtLiteralImpl]6))) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// uh-oh
            addReport([CtBinaryOperatorImpl][CtLiteralImpl]"Not enough astechs to work on mothballing of " + [CtInvocationImpl][CtVariableReadImpl]u.getHyperlinkedName());
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]u.setMothballTime([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getMothballTime() - [CtVariableReadImpl]minutes);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String report = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getHyperlinkedFullTitle() + [CtLiteralImpl]" spent ") + [CtVariableReadImpl]minutes) + [CtLiteralImpl]" minutes mothballing ") + [CtInvocationImpl][CtVariableReadImpl]u.getHyperlinkedName();
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isMothballing()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]u.completeMothball();
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtLiteralImpl]". Mothballing complete.";
        } else [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]". " + [CtInvocationImpl][CtVariableReadImpl]u.getMothballTime()) + [CtLiteralImpl]" minutes remaining.";
        }
        [CtInvocationImpl][CtVariableReadImpl]tech.setMinutesLeft([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft() - [CtVariableReadImpl]minutes);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isSelfCrewed()) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes -= [CtBinaryOperatorImpl][CtLiteralImpl]6 * [CtVariableReadImpl]minutes;
        }
        [CtInvocationImpl]addReport([CtVariableReadImpl]report);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Performs work to activate a unit.
     *
     * @param u
     * 		The unit on which to perform activation work.
     */
    public [CtTypeReferenceImpl]void activate([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isMothballed()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().warning([CtFieldReadImpl]mekhq.campaign.Campaign.class, [CtLiteralImpl]"activate(Unit)", [CtLiteralImpl]"Unit is already activated, cannot activate.");
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Person tech = [CtInvocationImpl][CtVariableReadImpl]u.getTech();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]tech) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// uh-oh
            addReport([CtBinaryOperatorImpl][CtLiteralImpl]"No tech assigned to the activation of " + [CtInvocationImpl][CtVariableReadImpl]u.getHyperlinkedName());
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// don't allow overtime minutes for activation because its cheating
        [CtCommentImpl]// since you don't roll
        [CtTypeReferenceImpl]int minutes = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft(), [CtInvocationImpl][CtVariableReadImpl]u.getMothballTime());
        [CtIfImpl][CtCommentImpl]// check astech time
        if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]u.isSelfCrewed()) && [CtBinaryOperatorImpl]([CtFieldReadImpl]astechPoolMinutes < [CtBinaryOperatorImpl]([CtVariableReadImpl]minutes * [CtLiteralImpl]6))) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// uh-oh
            addReport([CtBinaryOperatorImpl][CtLiteralImpl]"Not enough astechs to work on activation of " + [CtInvocationImpl][CtVariableReadImpl]u.getHyperlinkedName());
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]u.setMothballTime([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getMothballTime() - [CtVariableReadImpl]minutes);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String report = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getHyperlinkedFullTitle() + [CtLiteralImpl]" spent ") + [CtVariableReadImpl]minutes) + [CtLiteralImpl]" minutes activating ") + [CtInvocationImpl][CtVariableReadImpl]u.getHyperlinkedName();
        [CtInvocationImpl][CtVariableReadImpl]tech.setMinutesLeft([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft() - [CtVariableReadImpl]minutes);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isSelfCrewed()) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes -= [CtBinaryOperatorImpl][CtLiteralImpl]6 * [CtVariableReadImpl]minutes;
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isMothballing()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]u.completeActivation();
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtLiteralImpl]". Activation complete.";
        } else [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]". " + [CtInvocationImpl][CtVariableReadImpl]u.getMothballTime()) + [CtLiteralImpl]" minutes remaining.";
        }
        [CtInvocationImpl]addReport([CtVariableReadImpl]report);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void refit([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Refit r) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Person tech = [CtConditionalImpl]([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getUnit().getEngineer() == [CtLiteralImpl]null) ? [CtInvocationImpl]getPerson([CtInvocationImpl][CtVariableReadImpl]r.getTeamId()) : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getUnit().getEngineer();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]tech) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"No tech is assigned to refit " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getOriginalEntity().getShortName()) + [CtLiteralImpl]". Refit cancelled.");
            [CtInvocationImpl][CtVariableReadImpl]r.cancel();
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtInvocationImpl]getTargetFor([CtVariableReadImpl]r, [CtVariableReadImpl]tech);
        [CtIfImpl][CtCommentImpl]// check that all parts have arrived
        if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]r.acquireParts()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String report = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getHyperlinkedFullTitle() + [CtLiteralImpl]" works on ") + [CtInvocationImpl][CtVariableReadImpl]r.getPartName();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int minutes = [CtInvocationImpl][CtVariableReadImpl]r.getTimeLeft();
        [CtIfImpl][CtCommentImpl]// FIXME: Overtime?
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]minutes > [CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]r.addTimeSpent([CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft());
            [CtInvocationImpl][CtVariableReadImpl]tech.setMinutesLeft([CtLiteralImpl]0);
            [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]report + [CtLiteralImpl]", ") + [CtInvocationImpl][CtVariableReadImpl]r.getTimeLeft()) + [CtLiteralImpl]" minutes left.";
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]tech.setMinutesLeft([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft() - [CtVariableReadImpl]minutes);
            [CtInvocationImpl][CtVariableReadImpl]r.addTimeSpent([CtVariableReadImpl]minutes);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]r.hasFailedCheck()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]report + [CtLiteralImpl]", ") + [CtInvocationImpl][CtVariableReadImpl]r.succeed();
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int roll;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String wrongType = [CtLiteralImpl]"";
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]tech.isRightTechTypeFor([CtVariableReadImpl]r)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Utilities.roll3d6();
                    [CtAssignmentImpl][CtVariableWriteImpl]wrongType = [CtLiteralImpl]" <b>Warning: wrong tech type for this refit.</b>";
                }
                [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]report + [CtLiteralImpl]",  needs ") + [CtInvocationImpl][CtVariableReadImpl]target.getValueAsString()) + [CtLiteralImpl]" and rolls ") + [CtVariableReadImpl]roll) + [CtLiteralImpl]": ";
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]roll < [CtInvocationImpl][CtVariableReadImpl]target.getValue()) && [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useSupportEdge()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tech.getOptions().booleanOption([CtTypeAccessImpl]PersonnelOptions.EDGE_REPAIR_FAILED_REFIT)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getCurrentEdge() > [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]tech.setCurrentEdge([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getCurrentEdge() - [CtLiteralImpl]1);
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]tech.isRightTechTypeFor([CtVariableReadImpl]r)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
                    } else [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Utilities.roll3d6();
                    }
                    [CtIfImpl][CtCommentImpl]// This is needed to update the edge values of individual crewmen
                    if ([CtInvocationImpl][CtVariableReadImpl]tech.isEngineer()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]tech.setEdgeUsed([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getEdgeUsed() - [CtLiteralImpl]1);
                    }
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]" <b>failed!</b> but uses Edge to reroll...getting a " + [CtVariableReadImpl]roll) + [CtLiteralImpl]": ";
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]roll >= [CtInvocationImpl][CtVariableReadImpl]target.getValue()) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtInvocationImpl][CtVariableReadImpl]r.succeed();
                } else [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtInvocationImpl][CtVariableReadImpl]r.fail([CtTypeAccessImpl]SkillType.EXP_GREEN);
                    [CtIfImpl][CtCommentImpl]// try to refit again in case the tech has any time left
                    if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]r.isBeingRefurbished()) [CtBlockImpl]{
                        [CtInvocationImpl]refit([CtVariableReadImpl]r);
                    }
                }
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtVariableReadImpl]wrongType;
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartWorkEvent([CtVariableReadImpl]tech, [CtVariableReadImpl]r));
        [CtInvocationImpl]addReport([CtVariableReadImpl]report);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.parts.Part fixWarehousePart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part, [CtParameterImpl][CtTypeReferenceImpl]Person tech) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// get a new cloned part to work with and decrement original
        [CtTypeReferenceImpl]mekhq.campaign.parts.Part repairable = [CtInvocationImpl][CtVariableReadImpl]part.clone();
        [CtInvocationImpl][CtVariableReadImpl]part.decrementQuantity();
        [CtInvocationImpl]fixPart([CtVariableReadImpl]repairable, [CtVariableReadImpl]tech);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]repairable instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.OmniPod)) [CtBlockImpl]{
            [CtInvocationImpl]addPart([CtVariableReadImpl]repairable, [CtLiteralImpl]0);
        }
        [CtReturnImpl]return [CtVariableReadImpl]repairable;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Attempt to fix a part, which may have all kinds of effect depending on part type.
     *
     * @param partWork
     * 		- the {@link IPartWork} to be fixed
     * @param tech
     * 		- the {@link Person} who will attempt to fix the part
     * @return a <code>String</code> of the report that summarizes the outcome of the attempt to fix the part
     */
    public [CtTypeReferenceImpl]java.lang.String fixPart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IPartWork partWork, [CtParameterImpl][CtTypeReferenceImpl]Person tech) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtInvocationImpl]getTargetFor([CtVariableReadImpl]partWork, [CtVariableReadImpl]tech);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String report = [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String action = [CtLiteralImpl]" fix ";
        [CtIfImpl][CtCommentImpl]// TODO: this should really be a method on the part
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.AmmoBin) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]action = [CtLiteralImpl]" reload ";
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]partWork.isSalvaging()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]action = [CtLiteralImpl]" salvage ";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]action = [CtLiteralImpl]" replace ";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MekLocation) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.MekLocation) (partWork)).isBlownOff()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]action = [CtLiteralImpl]" re-attach ";
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.MekLocation) (partWork)).isBreached()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]action = [CtLiteralImpl]" seal ";
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]partWork.isSalvaging())) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (partWork)).isInSupply()) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtLiteralImpl]"<b>Not enough armor remaining.  Task suspended.</b>";
                [CtInvocationImpl]addReport([CtVariableReadImpl]report);
                [CtReturnImpl]return [CtVariableReadImpl]report;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.ProtomekArmor) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]partWork.isSalvaging())) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.ProtomekArmor) (partWork)).isInSupply()) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtLiteralImpl]"<b>Not enough Protomech armor remaining.  Task suspended.</b>";
                [CtInvocationImpl]addReport([CtVariableReadImpl]report);
                [CtReturnImpl]return [CtVariableReadImpl]report;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]partWork.isSalvaging())) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.BaArmor) (partWork)).isInSupply()) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtLiteralImpl]"<b>Not enough BA armor remaining.  Task suspended.</b>";
                [CtInvocationImpl]addReport([CtVariableReadImpl]report);
                [CtReturnImpl]return [CtVariableReadImpl]report;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.SpacecraftCoolingSystem) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtCommentImpl]// Change the string since we're not working on the part itself
            [CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getHyperlinkedFullTitle() + [CtLiteralImpl]" attempts to") + [CtVariableReadImpl]action) + [CtLiteralImpl]"a heat sink";
        } else [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getHyperlinkedFullTitle() + [CtLiteralImpl]" attempts to") + [CtVariableReadImpl]action) + [CtInvocationImpl][CtVariableReadImpl]partWork.getPartName();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]partWork.getUnit()) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtLiteralImpl]" on " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getName();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int minutes = [CtInvocationImpl][CtVariableReadImpl]partWork.getTimeLeft();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int minutesUsed = [CtVariableReadImpl]minutes;
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean usedOvertime = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]minutes > [CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft()) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]minutes -= [CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft();
            [CtIfImpl][CtCommentImpl]// check for overtime first
            if ([CtBinaryOperatorImpl][CtInvocationImpl]isOvertimeAllowed() && [CtBinaryOperatorImpl]([CtVariableReadImpl]minutes <= [CtInvocationImpl][CtVariableReadImpl]tech.getOvertimeLeft())) [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// we are working overtime
                [CtVariableWriteImpl]usedOvertime = [CtLiteralImpl]true;
                [CtInvocationImpl][CtVariableReadImpl]tech.setMinutesLeft([CtLiteralImpl]0);
                [CtInvocationImpl][CtVariableReadImpl]tech.setOvertimeLeft([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getOvertimeLeft() - [CtVariableReadImpl]minutes);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtCommentImpl]// we need to finish the task tomorrow
                [CtVariableWriteImpl]minutesUsed = [CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft();
                [CtLocalVariableImpl][CtTypeReferenceImpl]int overtimeUsed = [CtLiteralImpl]0;
                [CtIfImpl]if ([CtInvocationImpl]isOvertimeAllowed()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// Can't use more overtime than there are minutes remaining on the part
                    [CtVariableWriteImpl]overtimeUsed = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]minutes, [CtInvocationImpl][CtVariableReadImpl]tech.getOvertimeLeft());
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]minutesUsed += [CtVariableReadImpl]overtimeUsed;
                    [CtInvocationImpl][CtVariableReadImpl]partWork.setWorkedOvertime([CtLiteralImpl]true);
                    [CtAssignmentImpl][CtVariableWriteImpl]usedOvertime = [CtLiteralImpl]true;
                }
                [CtInvocationImpl][CtVariableReadImpl]partWork.addTimeSpent([CtVariableReadImpl]minutesUsed);
                [CtInvocationImpl][CtVariableReadImpl]tech.setMinutesLeft([CtLiteralImpl]0);
                [CtInvocationImpl][CtVariableReadImpl]tech.setOvertimeLeft([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getOvertimeLeft() - [CtVariableReadImpl]overtimeUsed);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int helpMod = [CtInvocationImpl]getShorthandedMod([CtInvocationImpl]getAvailableAstechs([CtVariableReadImpl]minutesUsed, [CtVariableReadImpl]usedOvertime), [CtLiteralImpl]false);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]partWork.getUnit()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Dropship) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Jumpship))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtLiteralImpl]0;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getShorthandedMod() < [CtVariableReadImpl]helpMod) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]partWork.setShorthandedMod([CtVariableReadImpl]helpMod);
                }
                [CtInvocationImpl][CtVariableReadImpl]partWork.setTeamId([CtInvocationImpl][CtVariableReadImpl]tech.getId());
                [CtInvocationImpl][CtVariableReadImpl]partWork.reservePart();
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtLiteralImpl]" - <b>Not enough time, the remainder of the task";
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]partWork.getUnit()) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtLiteralImpl]" on " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getName();
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]minutesUsed > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtLiteralImpl]" will be finished tomorrow.</b>";
                } else [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtLiteralImpl]" cannot be finished because there was no time left after maintenance tasks.</b>";
                    [CtInvocationImpl][CtVariableReadImpl]partWork.resetTimeSpent();
                    [CtInvocationImpl][CtVariableReadImpl]partWork.resetOvertime();
                    [CtInvocationImpl][CtVariableReadImpl]partWork.setTeamId([CtLiteralImpl]null);
                    [CtInvocationImpl][CtVariableReadImpl]partWork.cancelReservation();
                }
                [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartWorkEvent([CtVariableReadImpl]tech, [CtVariableReadImpl]partWork));
                [CtInvocationImpl]addReport([CtVariableReadImpl]report);
                [CtReturnImpl]return [CtVariableReadImpl]report;
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]tech.setMinutesLeft([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft() - [CtVariableReadImpl]minutes);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int astechMinutesUsed = [CtBinaryOperatorImpl][CtVariableReadImpl]minutesUsed * [CtInvocationImpl]getAvailableAstechs([CtVariableReadImpl]minutesUsed, [CtVariableReadImpl]usedOvertime);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]astechPoolMinutes < [CtVariableReadImpl]astechMinutesUsed) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]astechMinutesUsed -= [CtFieldReadImpl]astechPoolMinutes;
            [CtAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes = [CtLiteralImpl]0;
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolOvertime -= [CtVariableReadImpl]astechMinutesUsed;
        } else [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes -= [CtVariableReadImpl]astechMinutesUsed;
        }
        [CtLocalVariableImpl][CtCommentImpl]// check for the type
        [CtTypeReferenceImpl]int roll;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String wrongType = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]tech.isRightTechTypeFor([CtVariableReadImpl]partWork)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Utilities.roll3d6();
            [CtAssignmentImpl][CtVariableWriteImpl]wrongType = [CtLiteralImpl]" <b>Warning: wrong tech type for this repair.</b>";
        }
        [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]report + [CtLiteralImpl]",  needs ") + [CtInvocationImpl][CtVariableReadImpl]target.getValueAsString()) + [CtLiteralImpl]" and rolls ") + [CtVariableReadImpl]roll) + [CtLiteralImpl]":";
        [CtLocalVariableImpl][CtTypeReferenceImpl]int xpGained = [CtLiteralImpl]0;
        [CtIfImpl][CtCommentImpl]// if we fail and would break a part, here's a chance to use Edge for a reroll...
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useSupportEdge() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tech.getOptions().booleanOption([CtTypeAccessImpl]PersonnelOptions.EDGE_REPAIR_BREAK_PART)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getCurrentEdge() > [CtLiteralImpl]0)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().isDestroyByMargin() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getDestroyMargin() <= [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() - [CtVariableReadImpl]roll))) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().isDestroyByMargin()) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getExperienceLevel([CtLiteralImpl]false) == [CtFieldReadImpl]SkillType.EXP_ELITE)[CtCommentImpl]// if an elite, primary tech and destroy by margin is NOT on
             || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getPrimaryRole() == [CtFieldReadImpl]Person.T_SPACE_CREW)))[CtCommentImpl]// For vessel crews
             && [CtBinaryOperatorImpl]([CtVariableReadImpl]roll < [CtInvocationImpl][CtVariableReadImpl]target.getValue()))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]tech.setCurrentEdge([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getCurrentEdge() - [CtLiteralImpl]1);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]tech.isRightTechTypeFor([CtVariableReadImpl]partWork)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtInvocationImpl][CtTypeAccessImpl]Utilities.roll3d6();
                }
                [CtIfImpl][CtCommentImpl]// This is needed to update the edge values of individual crewmen
                if ([CtInvocationImpl][CtVariableReadImpl]tech.isEngineer()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]tech.setEdgeUsed([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getEdgeUsed() + [CtLiteralImpl]1);
                }
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]" <b>failed!</b> and would destroy the part, but uses Edge to reroll...getting a " + [CtVariableReadImpl]roll) + [CtLiteralImpl]":";
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]roll >= [CtInvocationImpl][CtVariableReadImpl]target.getValue()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtVariableReadImpl]report + [CtInvocationImpl][CtVariableReadImpl]partWork.succeed();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().payForRepairs() && [CtInvocationImpl][CtVariableReadImpl]action.equals([CtLiteralImpl]" fix ")) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor))) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Money cost = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Part) (partWork)).getStickerPrice().multipliedBy([CtLiteralImpl]0.2);
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<br>Repairs cost " + [CtInvocationImpl][CtVariableReadImpl]cost.toAmountAndSymbolString()) + [CtLiteralImpl]" worth of parts.";
                [CtInvocationImpl][CtFieldReadImpl]finances.debit([CtVariableReadImpl]cost, [CtTypeAccessImpl]Transaction.C_REPAIRS, [CtBinaryOperatorImpl][CtLiteralImpl]"Repair of " + [CtInvocationImpl][CtVariableReadImpl]partWork.getPartName(), [CtInvocationImpl]getLocalDate());
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]roll == [CtLiteralImpl]12) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS)) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]xpGained += [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getSuccessXP();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]tech.setNTasks([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getNTasks() + [CtLiteralImpl]1);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getNTasks() >= [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getNTasksXP()) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]xpGained += [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getTaskXP();
                [CtInvocationImpl][CtVariableReadImpl]tech.setNTasks([CtLiteralImpl]0);
            }
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int modePenalty = [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getMode().expReduction;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int effectiveSkillLvl = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tech.getSkillForWorkingOn([CtVariableReadImpl]partWork).getExperienceLevel() - [CtVariableReadImpl]modePenalty;
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().isDestroyByMargin()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getDestroyMargin() > [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() - [CtVariableReadImpl]roll)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// not destroyed - set the effective level as low as
                    [CtCommentImpl]// possible
                    [CtVariableWriteImpl]effectiveSkillLvl = [CtFieldReadImpl]SkillType.EXP_ULTRA_GREEN;
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtCommentImpl]// destroyed - set the effective level to elite
                    [CtVariableWriteImpl]effectiveSkillLvl = [CtFieldReadImpl]SkillType.EXP_ELITE;
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]report = [CtBinaryOperatorImpl][CtVariableReadImpl]report + [CtInvocationImpl][CtVariableReadImpl]partWork.fail([CtVariableReadImpl]effectiveSkillLvl);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]roll == [CtLiteralImpl]2) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]target.getValue() != [CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL)) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]xpGained += [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getMistakeXP();
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]xpGained > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]tech.awardXP([CtVariableReadImpl]xpGained);
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]" (" + [CtVariableReadImpl]xpGained) + [CtLiteralImpl]"XP gained) ";
        }
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]report += [CtVariableReadImpl]wrongType;
        [CtInvocationImpl][CtVariableReadImpl]partWork.resetTimeSpent();
        [CtInvocationImpl][CtVariableReadImpl]partWork.resetOvertime();
        [CtInvocationImpl][CtVariableReadImpl]partWork.setTeamId([CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]partWork.cancelReservation();
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartWorkEvent([CtVariableReadImpl]tech, [CtVariableReadImpl]partWork));
        [CtInvocationImpl]addReport([CtVariableReadImpl]report);
        [CtReturnImpl]return [CtVariableReadImpl]report;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Parses news file and loads news items for the current year.
     */
    public [CtTypeReferenceImpl]void reloadNews() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]news.loadNewsFor([CtInvocationImpl]getGameYear(), [CtInvocationImpl][CtFieldReadImpl]id.getLeastSignificantBits());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Checks for a news item for the current date. If found, adds it to the daily report.
     */
    public [CtTypeReferenceImpl]void readNews() [CtBlockImpl]{
        [CtForEachImpl][CtCommentImpl]// read the news
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.NewsItem article : [CtInvocationImpl][CtFieldReadImpl]news.fetchNewsFor([CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtInvocationImpl][CtVariableReadImpl]article.getHeadlineForReport());
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.NewsItem article : [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getPlanetaryNews([CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtInvocationImpl][CtVariableReadImpl]article.getHeadlineForReport());
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getDeploymentDeficit([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract contract) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]contract.isActive()) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Inactive contracts have no deficits.
            return [CtLiteralImpl]0;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contract.getStartDate().compareTo([CtInvocationImpl]getLocalDate()) >= [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// Do not check for deficits if the contract has not started or
            [CtCommentImpl]// it is the first day of the contract, as players won't have
            [CtCommentImpl]// had time to assign forces to the contract yet
            return [CtLiteralImpl]0;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int total = [CtUnaryOperatorImpl]-[CtInvocationImpl][CtVariableReadImpl]contract.getRequiredLances();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int role = [CtUnaryOperatorImpl]-[CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]1, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]contract.getRequiredLances() / [CtLiteralImpl]2);
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Lance l : [CtInvocationImpl][CtFieldReadImpl]lances.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]l.getMissionId() == [CtInvocationImpl][CtVariableReadImpl]contract.getId()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]l.getRole() != [CtFieldReadImpl]mekhq.campaign.againstTheBot.enums.AtBLanceRole.UNASSIGNED)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]total++;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]l.getRole() == [CtInvocationImpl][CtVariableReadImpl]contract.getRequiredLanceType()) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]role++;
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]total >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]role >= [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]0;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.abs([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]total, [CtVariableReadImpl]role));
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processNewDayATBScenarios() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission m : [CtInvocationImpl]getMissions()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]m.isActive()) || [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]m instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract))) || [CtInvocationImpl][CtInvocationImpl]getLocalDate().isBefore([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.Contract) (m)).getStartDate())) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl][CtCommentImpl]/* Situations like a delayed start or running out of funds during transit can
            delay arrival until after the contract start. In that case, shift the
            starting and ending dates before making any battle rolls. We check that the
            unit is actually on route to the planet in case the user is using a custom
            system for transport or splitting the unit, etc.
             */
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getLocation().isOnPlanet()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLocation().getJumpPath().isEmpty())) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getLocation().getJumpPath().getLastSystem().getId().equals([CtInvocationImpl][CtVariableReadImpl]m.getSystemId())) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// transitTime is measured in days; so we round up to the next whole day
                [CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)).setStartAndEndDate([CtInvocationImpl][CtInvocationImpl]getLocalDate().plusDays([CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtInvocationImpl][CtInvocationImpl]getLocation().getTransitTime())))));
                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"The start and end dates of " + [CtInvocationImpl][CtVariableReadImpl]m.getName()) + [CtLiteralImpl]" have been shifted to reflect the current ETA.");
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getLocalDate().getDayOfWeek() == [CtFieldReadImpl][CtTypeAccessImpl]java.time.DayOfWeek.[CtFieldReferenceImpl]MONDAY) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int deficit = [CtInvocationImpl]getDeploymentDeficit([CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]deficit > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)).addPlayerMinorBreaches([CtVariableReadImpl]deficit);
                    [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Failure to meet " + [CtInvocationImpl][CtVariableReadImpl]m.getName()) + [CtLiteralImpl]" requirements resulted in ") + [CtVariableReadImpl]deficit) + [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]deficit == [CtLiteralImpl]1 ? [CtLiteralImpl]" minor contract breach" : [CtLiteralImpl]" minor contract breaches"));
                }
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Scenario s : [CtInvocationImpl][CtVariableReadImpl]m.getScenarios()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]s.isCurrent()) || [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]s instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBScenario))) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]s.getDate() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]s.getDate().isBefore([CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]s.setStatus([CtTypeAccessImpl]Scenario.S_DEFEAT);
                    [CtInvocationImpl][CtVariableReadImpl]s.clearAllForcesAndPersonnel([CtThisAccessImpl]this);
                    [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)).addPlayerMinorBreach();
                    [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Failure to deploy for " + [CtInvocationImpl][CtVariableReadImpl]s.getName()) + [CtLiteralImpl]" resulted in defeat and a minor contract breach.");
                    [CtInvocationImpl][CtVariableReadImpl]s.generateStub([CtThisAccessImpl]this);
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getLocalDate().getDayOfWeek() == [CtFieldReadImpl][CtTypeAccessImpl]java.time.DayOfWeek.[CtFieldReferenceImpl]MONDAY) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.mission.atb.AtBScenarioFactory.createScenariosForNewWeek([CtThisAccessImpl]this);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission m : [CtInvocationImpl]getMissions()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]m.isActive() && [CtBinaryOperatorImpl]([CtVariableReadImpl]m instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)).getStartDate().isAfter([CtInvocationImpl]getLocalDate()))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)).checkEvents([CtThisAccessImpl]this);
            }
            [CtForEachImpl][CtCommentImpl]/* If there is a standard battle set for today, deploy the lance. */
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Scenario s : [CtInvocationImpl][CtVariableReadImpl]m.getScenarios()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]s.getDate() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]s.getDate().equals([CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int forceId = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBScenario) (s)).getLanceForceId();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]lances.get([CtVariableReadImpl]forceId) != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtVariableReadImpl]forceId).isDeployed())) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtCommentImpl]// If any unit in the force is under repair, don't deploy the force
                        [CtCommentImpl]// Merely removing the unit from deployment would break with user expectation
                        [CtTypeReferenceImpl]boolean forceUnderRepair = [CtLiteralImpl]false;
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID uid : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtVariableReadImpl]forceId).getAllUnits([CtLiteralImpl]true)) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]uid);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]u != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]u.isUnderRepair()) [CtBlockImpl]{
                                [CtAssignmentImpl][CtVariableWriteImpl]forceUnderRepair = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                        }
                        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]forceUnderRepair) [CtBlockImpl]{
                            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtVariableReadImpl]forceId).setScenarioId([CtInvocationImpl][CtVariableReadImpl]s.getId());
                            [CtInvocationImpl][CtVariableReadImpl]s.addForces([CtVariableReadImpl]forceId);
                            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID uid : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtVariableReadImpl]forceId).getAllUnits([CtLiteralImpl]true)) [CtBlockImpl]{
                                [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]uid);
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]u) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]u.setScenarioId([CtInvocationImpl][CtVariableReadImpl]s.getId());
                                }
                            }
                            [CtInvocationImpl]addReport([CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtFieldReadImpl]resources.getString([CtLiteralImpl]"atbMissionTodayWithForce.format"), [CtInvocationImpl][CtVariableReadImpl]s.getName(), [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtVariableReadImpl]forceId).getName()));
                            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.DeploymentChangedEvent([CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtVariableReadImpl]forceId), [CtVariableReadImpl]s));
                        } else [CtBlockImpl]{
                            [CtInvocationImpl]addReport([CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtFieldReadImpl]resources.getString([CtLiteralImpl]"atbMissionToday.format"), [CtInvocationImpl][CtVariableReadImpl]s.getName()));
                        }
                    } else [CtBlockImpl]{
                        [CtInvocationImpl]addReport([CtInvocationImpl][CtTypeAccessImpl]java.text.MessageFormat.format([CtInvocationImpl][CtFieldReadImpl]resources.getString([CtLiteralImpl]"atbMissionToday.format"), [CtInvocationImpl][CtVariableReadImpl]s.getName()));
                    }
                }
            }
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processNewDayATBFatigue() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean inContract = [CtLiteralImpl]false;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission m : [CtInvocationImpl]getMissions()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]m.isActive()) || [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]m instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract))) || [CtInvocationImpl][CtInvocationImpl]getLocalDate().isBefore([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.Contract) (m)).getStartDate())) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)).getMissionType()) {
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_GARRISONDUTY :
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_SECURITYDUTY :
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_CADREDUTY :
                    [CtOperatorAssignmentImpl][CtFieldWriteImpl]fatigueLevel -= [CtLiteralImpl]1;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_RIOTDUTY :
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_GUERRILLAWARFARE :
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_PIRATEHUNTING :
                    [CtOperatorAssignmentImpl][CtFieldWriteImpl]fatigueLevel += [CtLiteralImpl]1;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_RELIEFDUTY :
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_PLANETARYASSAULT :
                    [CtOperatorAssignmentImpl][CtFieldWriteImpl]fatigueLevel += [CtLiteralImpl]2;
                    [CtBreakImpl]break;
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_DIVERSIONARYRAID :
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_EXTRACTIONRAID :
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_RECONRAID :
                [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.mission.AtBContract.MT_OBJECTIVERAID :
                    [CtOperatorAssignmentImpl][CtFieldWriteImpl]fatigueLevel += [CtLiteralImpl]3;
                    [CtBreakImpl]break;
            }
            [CtAssignmentImpl][CtVariableWriteImpl]inContract = [CtLiteralImpl]true;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]inContract) && [CtInvocationImpl][CtFieldReadImpl]location.isOnPlanet()) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]fatigueLevel -= [CtLiteralImpl]2;
        }
        [CtAssignmentImpl][CtFieldWriteImpl]fatigueLevel = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtFieldReadImpl]fatigueLevel, [CtLiteralImpl]0);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void processNewDayATB() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]contractMarket.generateContractOffers([CtThisAccessImpl]this);
        [CtInvocationImpl][CtFieldReadImpl]unitMarket.generateUnitOffers([CtThisAccessImpl]this);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl]getShipSearchExpiration() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getShipSearchExpiration().isAfter([CtInvocationImpl]getLocalDate()))) [CtBlockImpl]{
            [CtInvocationImpl]setShipSearchExpiration([CtLiteralImpl]null);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]getShipSearchResult() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"Opportunity for purchase of " + [CtInvocationImpl]getShipSearchResult()) + [CtLiteralImpl]" has expired.");
                [CtInvocationImpl]setShipSearchResult([CtLiteralImpl]null);
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getLocalDate().getDayOfWeek() == [CtFieldReadImpl][CtTypeAccessImpl]java.time.DayOfWeek.[CtFieldReferenceImpl]MONDAY) [CtBlockImpl]{
            [CtInvocationImpl]processShipSearch();
        }
        [CtIfImpl][CtCommentImpl]// Add or remove dependents - only if one of the two options makes this possible is enabled
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getLocalDate().getDayOfYear() == [CtLiteralImpl]1) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getDependentsNeverLeave()) || [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().canAtBAddDependents())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int numPersonnel = [CtLiteralImpl]0;
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Person> dependents = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]numPersonnel++;
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isDependent()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]dependents.add([CtVariableReadImpl]p);
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int roll = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2) + [CtInvocationImpl]getUnitRatingMod()) - [CtLiteralImpl]2;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]roll < [CtLiteralImpl]2) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtLiteralImpl]2;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]roll > [CtLiteralImpl]12) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]roll = [CtLiteralImpl]12;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int change = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]numPersonnel * [CtBinaryOperatorImpl]([CtVariableReadImpl]roll - [CtLiteralImpl]5)) / [CtLiteralImpl]100;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]change < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getDependentsNeverLeave()) [CtBlockImpl]{
                    [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]change < [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]dependents.size() > [CtLiteralImpl]0)) [CtBlockImpl]{
                        [CtInvocationImpl]removePerson([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Utilities.getRandomItem([CtVariableReadImpl]dependents).getId());
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]change++;
                    } 
                }
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().canAtBAddDependents()) [CtBlockImpl]{
                [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]change; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Person p = [CtInvocationImpl]newDependent([CtTypeAccessImpl]Person.T_ASTECH, [CtLiteralImpl]false);
                    [CtInvocationImpl]recruitPerson([CtVariableReadImpl]p);
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getLocalDate().getDayOfMonth() == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]/* First of the month; roll morale, track unit fatigue. */
            [CtTypeReferenceImpl]mekhq.campaign.rating.IUnitRating rating = [CtInvocationImpl]getUnitRating();
            [CtInvocationImpl][CtVariableReadImpl]rating.reInitialize();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission m : [CtInvocationImpl]getMissions()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]m.isActive() && [CtBinaryOperatorImpl]([CtVariableReadImpl]m instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)).getStartDate().isAfter([CtInvocationImpl]getLocalDate()))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)).checkMorale([CtInvocationImpl]getLocalDate(), [CtInvocationImpl]getUnitRatingMod());
                    [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Enemy morale is now " + [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (m)).getMoraleLevelName()) + [CtLiteralImpl]" on contract ") + [CtInvocationImpl][CtVariableReadImpl]m.getName());
                }
            }
            [CtIfImpl][CtCommentImpl]// Account for fatigue
            if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getTrackUnitFatigue()) [CtBlockImpl]{
                [CtInvocationImpl]processNewDayATBFatigue();
            }
        }
        [CtInvocationImpl]processNewDayATBScenarios();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void processNewDayPersonnel() [CtBlockImpl]{
        [CtForEachImpl][CtCommentImpl]// This MUST use getActivePersonnel as we only want to process active personnel, and
        [CtCommentImpl]// furthermore this allows us to add and remove personnel without issue
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Random Death
            [CtCommentImpl]// Random Marriages
            if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useRandomMarriages()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]p.randomMarriage([CtThisAccessImpl]this);
            }
            [CtInvocationImpl][CtVariableReadImpl]p.resetMinutesLeft();
            [CtInvocationImpl][CtCommentImpl]// Reset acquisitions made to 0
            [CtVariableReadImpl]p.setAcquisition([CtLiteralImpl]0);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.needsFixing() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useAdvancedMedical())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]p.decrementDaysToWaitForHealing();
                [CtLocalVariableImpl][CtTypeReferenceImpl]Person doctor = [CtInvocationImpl]getPerson([CtInvocationImpl][CtVariableReadImpl]p.getDoctorId());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]doctor != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]doctor.isDoctor()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getDaysToWaitForHealing() <= [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtInvocationImpl]addReport([CtInvocationImpl]healPerson([CtVariableReadImpl]p, [CtVariableReadImpl]doctor));
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.checkNaturalHealing([CtLiteralImpl]15)) [CtBlockImpl]{
                    [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getHyperlinkedFullTitle() + [CtLiteralImpl]" heals naturally!");
                    [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtInvocationImpl][CtVariableReadImpl]p.getUnitId());
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]u != [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]u.resetPilotAndEntity();
                    }
                }
            }
            [CtIfImpl][CtCommentImpl]// TODO Advanced Medical needs to go away from here later on
            if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useAdvancedMedical()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.mod.am.InjuryUtil.resolveDailyHealing([CtThisAccessImpl]this, [CtVariableReadImpl]p);
                [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtInvocationImpl][CtVariableReadImpl]p.getUnitId());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]u != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]u.resetPilotAndEntity();
                }
            }
            [CtIfImpl][CtCommentImpl]// TODO : Reset this based on hasSupportRole(false) instead of checking for each type
            [CtCommentImpl]// TODO : p.isEngineer will need to stay, however
            [CtCommentImpl]// Reset edge points to the purchased value each week. This should only
            [CtCommentImpl]// apply for support personnel - combat troops reset with each new mm game
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.isAdmin() || [CtInvocationImpl][CtVariableReadImpl]p.isDoctor()) || [CtInvocationImpl][CtVariableReadImpl]p.isEngineer()) || [CtInvocationImpl][CtVariableReadImpl]p.isTech()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getLocalDate().getDayOfWeek() == [CtFieldReadImpl][CtTypeAccessImpl]java.time.DayOfWeek.[CtFieldReferenceImpl]MONDAY)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]p.resetCurrentEdge();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getIdleXP() > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getLocalDate().getDayOfMonth() == [CtLiteralImpl]1)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus().isPrisoner())) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// Prisoners can't gain XP, while Bondsmen can gain xp
                [CtVariableReadImpl]p.setIdleMonths([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getIdleMonths() + [CtLiteralImpl]1);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getIdleMonths() >= [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getMonthsIdleXP()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2) >= [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getTargetIdleXP()) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]p.awardXP([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getIdleXP());
                        [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getHyperlinkedFullTitle() + [CtLiteralImpl]" has gained ") + [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getIdleXP()) + [CtLiteralImpl]" XP");
                    }
                    [CtInvocationImpl][CtVariableReadImpl]p.setIdleMonths([CtLiteralImpl]0);
                }
            }
            [CtIfImpl][CtCommentImpl]// Procreation
            if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getGender().isFemale()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isPregnant()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useUnofficialProcreation()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getLocalDate().compareTo([CtInvocationImpl][CtVariableReadImpl]p.getDueDate()) == [CtLiteralImpl]0) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]p.birth([CtThisAccessImpl]this);
                        }
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]p.removePregnancy();
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useUnofficialProcreation()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]p.procreate([CtThisAccessImpl]this);
                }
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void processNewDayUnits() [CtBlockImpl]{
        [CtForEachImpl][CtCommentImpl]// need to loop through units twice, the first time to do all maintenance and
        [CtCommentImpl]// the second
        [CtCommentImpl]// time to do whatever else. Otherwise, maintenance minutes might get sucked up
        [CtCommentImpl]// by other
        [CtCommentImpl]// stuff. This is also a good place to ensure that a unit's engineer gets reset
        [CtCommentImpl]// and updated.
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]u.resetEngineer();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]u.getEngineer()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEngineer().resetMinutesLeft();
            }
            [CtInvocationImpl][CtCommentImpl]// do maintenance checks
            doMaintenance([CtVariableReadImpl]u);
        }
        [CtLocalVariableImpl][CtCommentImpl]// need to check for assigned tasks in two steps to avoid
        [CtCommentImpl]// concurrent mod problems
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Integer> assignedPartIds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.Integer> arrivedPartIds = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part : [CtInvocationImpl]getParts()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Refit) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]part.getTeamId() != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]assignedPartIds.add([CtInvocationImpl][CtVariableReadImpl]part.getId());
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]part.checkArrival()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]arrivedPartIds.add([CtInvocationImpl][CtVariableReadImpl]part.getId());
            }
        }
        [CtForEachImpl][CtCommentImpl]// arrive parts before attempting refit or parts will not get reserved that day
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int pid : [CtVariableReadImpl]arrivedPartIds) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part = [CtInvocationImpl]getPart([CtVariableReadImpl]pid);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]part) [CtBlockImpl]{
                [CtInvocationImpl]arrivePart([CtVariableReadImpl]part);
            }
        }
        [CtForEachImpl][CtCommentImpl]// finish up any overnight assigned tasks
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]int pid : [CtVariableReadImpl]assignedPartIds) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part = [CtInvocationImpl]getPart([CtVariableReadImpl]pid);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]part) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Person tech;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]part.getUnit() != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]part.getUnit().getEngineer() != [CtLiteralImpl]null)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]tech = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]part.getUnit().getEngineer();
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]tech = [CtInvocationImpl]getPerson([CtInvocationImpl][CtVariableReadImpl]part.getTeamId());
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]tech) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]tech.getSkillForWorkingOn([CtVariableReadImpl]part)) [CtBlockImpl]{
                        [CtInvocationImpl]fixPart([CtVariableReadImpl]part, [CtVariableReadImpl]tech);
                    } else [CtBlockImpl]{
                        [CtInvocationImpl]addReport([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%s looks at %s, recalls his total lack of skill for working with such technology, then slowly puts the tools down before anybody gets hurt.", [CtInvocationImpl][CtVariableReadImpl]tech.getHyperlinkedFullTitle(), [CtInvocationImpl][CtVariableReadImpl]part.getName()));
                        [CtInvocationImpl][CtVariableReadImpl]part.setTeamId([CtLiteralImpl]null);
                    }
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtLiteralImpl]null, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Could not find tech for part: " + [CtInvocationImpl][CtVariableReadImpl]part.getName()) + [CtLiteralImpl]" on unit: ") + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]part.getUnit().getHyperlinkedName(), [CtLiteralImpl]"Invalid Auto-continue", [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]ERROR_MESSAGE);
                }
                [CtIfImpl][CtCommentImpl]// check to see if this part can now be combined with other
                [CtCommentImpl]// spare parts
                if ([CtInvocationImpl][CtVariableReadImpl]part.isSpare()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part spare = [CtInvocationImpl]checkForExistingSparePart([CtVariableReadImpl]part);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]spare) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]spare.incrementQuantity();
                        [CtInvocationImpl]removePart([CtVariableReadImpl]part);
                    }
                }
            }
        }
        [CtLocalVariableImpl][CtCommentImpl]// ok now we can check for other stuff we might need to do to units
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.UUID> unitsToRemove = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]u.isRefitting()) [CtBlockImpl]{
                [CtInvocationImpl]refit([CtInvocationImpl][CtVariableReadImpl]u.getRefit());
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]u.isMothballing()) [CtBlockImpl]{
                [CtInvocationImpl]workOnMothballingOrActivation([CtVariableReadImpl]u);
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isPresent()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]u.checkArrival();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]u.isRepairable()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]u.hasSalvageableParts())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]unitsToRemove.add([CtInvocationImpl][CtVariableReadImpl]u.getId());
            }
        }
        [CtInvocationImpl][CtCommentImpl]// Remove any unrepairable, unsalvageable units
        [CtVariableReadImpl]unitsToRemove.forEach([CtExecutableReferenceExpressionImpl][CtThisAccessImpl]this::removeUnit);
        [CtIfImpl][CtCommentImpl]// Finally, run Mass Repair Mass Salvage if desired
        if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getMekHQOptions().getNewDayMRMS()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]mekhq.service.MassRepairService.massRepairSalvageAllUnits([CtThisAccessImpl]this);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return <code>true</code> if the new day arrived
     */
    public [CtTypeReferenceImpl]boolean newDay() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.DayEndingEvent([CtThisAccessImpl]this))) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]false;
        }
        [CtInvocationImpl][CtCommentImpl]// Autosave based on the previous day's information
        [CtFieldReadImpl][CtThisAccessImpl]this.autosaveService.requestDayAdvanceAutosave([CtThisAccessImpl]this);
        [CtAssignmentImpl][CtCommentImpl]// Advance the day by one
        [CtFieldWriteImpl]currentDay = [CtInvocationImpl][CtFieldReadImpl]currentDay.plus([CtLiteralImpl]1, [CtFieldReadImpl][CtTypeAccessImpl]java.time.temporal.ChronoUnit.[CtFieldReferenceImpl]DAYS);
        [CtIfImpl][CtCommentImpl]// Determine if we have an active contract or not, as this can get used elsewhere before
        [CtCommentImpl]// we actually hit the AtB new day (e.g. personnel market)
        if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUseAtB()) [CtBlockImpl]{
            [CtInvocationImpl]setHasActiveContract();
        }
        [CtInvocationImpl][CtCommentImpl]// Clear Reports
        [CtInvocationImpl]getCurrentReport().clear();
        [CtInvocationImpl]setCurrentReportHTML([CtLiteralImpl]"");
        [CtInvocationImpl][CtFieldReadImpl]newReports.clear();
        [CtInvocationImpl]beginReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<b>" + [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getMekHQOptions().getLongDisplayFormattedDate([CtInvocationImpl]getLocalDate())) + [CtLiteralImpl]"</b>");
        [CtIfImpl][CtCommentImpl]// New Year Changes
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getLocalDate().getDayOfYear() == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// News is reloaded
            reloadNews();
            [CtInvocationImpl][CtCommentImpl]// Change Year Game Option
            [CtInvocationImpl][CtInvocationImpl]getGameOptions().getOption([CtLiteralImpl]"year").setValue([CtInvocationImpl]getGameYear());
        }
        [CtInvocationImpl]readNews();
        [CtInvocationImpl][CtInvocationImpl]getLocation().newDay([CtThisAccessImpl]this);
        [CtInvocationImpl][CtCommentImpl]// Manage the personnel market
        [CtInvocationImpl]getPersonnelMarket().generatePersonnelForDay([CtThisAccessImpl]this);
        [CtIfImpl][CtCommentImpl]// Process New Day for AtB
        if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUseAtB()) [CtBlockImpl]{
            [CtInvocationImpl]processNewDayATB();
        }
        [CtInvocationImpl]processNewDayPersonnel();
        [CtInvocationImpl]resetAstechMinutes();
        [CtInvocationImpl]processNewDayUnits();
        [CtInvocationImpl]setShoppingList([CtInvocationImpl]goShopping([CtInvocationImpl]getShoppingList()));
        [CtInvocationImpl][CtCommentImpl]// check for anything in finances
        [CtInvocationImpl]getFinances().newDay([CtThisAccessImpl]this);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.NewDayEvent([CtThisAccessImpl]this));
        [CtReturnImpl]return [CtLiteralImpl]true;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return a list of all currently active contracts
     */
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.mission.Contract> getActiveContracts() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.mission.Contract> active = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission mission : [CtInvocationImpl]getMissions()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]mission instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract contract = [CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.Contract) (mission));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contract.isActive() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getLocalDate().isAfter([CtInvocationImpl][CtVariableReadImpl]contract.getEndingDate()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getLocalDate().isBefore([CtInvocationImpl][CtVariableReadImpl]contract.getStartDate()))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]active.add([CtVariableReadImpl]contract);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]active;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return whether or not the current campaign has an active contract for the current date
     */
    public [CtTypeReferenceImpl]boolean hasActiveContract() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]hasActiveContract;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is used to check if the current campaign has one or more active contacts, and sets the
     * value of hasActiveContract based on that check. This value should not be set elsewhere
     */
    public [CtTypeReferenceImpl]void setHasActiveContract() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]hasActiveContract = [CtLiteralImpl]false;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission mission : [CtInvocationImpl]getMissions()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]mission instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract contract = [CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.Contract) (mission));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contract.isActive() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getLocalDate().isAfter([CtInvocationImpl][CtVariableReadImpl]contract.getEndingDate()))) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getLocalDate().isBefore([CtInvocationImpl][CtVariableReadImpl]contract.getStartDate()))) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]hasActiveContract = [CtLiteralImpl]true;
                [CtBreakImpl]break;
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Person getFlaggedCommander() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isCommander()) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]p;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getPayRoll() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getPayRoll([CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getPayRoll([CtParameterImpl][CtTypeReferenceImpl]boolean noInfantry) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().payForSalaries()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getTheoreticalPayroll([CtVariableReadImpl]noInfantry);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
        }
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]mekhq.campaign.Money getTheoreticalPayroll([CtParameterImpl][CtTypeReferenceImpl]boolean noInfantry) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money salaries = [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// Optionized infantry (Unofficial)
            if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtVariableReadImpl]noInfantry && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_INFANTRY))) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]salaries = [CtInvocationImpl][CtVariableReadImpl]salaries.plus([CtInvocationImpl][CtVariableReadImpl]p.getSalary());
            }
        }
        [CtAssignmentImpl][CtCommentImpl]// add in astechs from the astech pool
        [CtCommentImpl]// we will assume Mech Tech * able-bodied * enlisted (changed from vee mechanic)
        [CtCommentImpl]// 800 * 0.5 * 0.6 = 240
        [CtVariableWriteImpl]salaries = [CtInvocationImpl][CtVariableReadImpl]salaries.plus([CtBinaryOperatorImpl][CtLiteralImpl]240.0 * [CtFieldReadImpl]astechPool);
        [CtAssignmentImpl][CtVariableWriteImpl]salaries = [CtInvocationImpl][CtVariableReadImpl]salaries.plus([CtBinaryOperatorImpl][CtLiteralImpl]320.0 * [CtFieldReadImpl]medicPool);
        [CtReturnImpl]return [CtVariableReadImpl]salaries;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getMaintenanceCosts() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]campaignOptions.payForMaintain()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().filter([CtLambdaImpl]([CtParameterImpl] u) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.requiresMaintenance() && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]u.getTech())).map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getMaintenanceCost).reduce([CtInvocationImpl][CtTypeAccessImpl]Money.zero(), [CtLambdaImpl]([CtParameterImpl] a,[CtParameterImpl] b) -> [CtInvocationImpl][CtVariableReadImpl]a.plus([CtVariableReadImpl]b));
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getWeeklyMaintenanceCosts() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getWeeklyMaintenanceCost).reduce([CtInvocationImpl][CtTypeAccessImpl]Money.zero(), [CtLambdaImpl]([CtParameterImpl] a,[CtParameterImpl] b) -> [CtInvocationImpl][CtVariableReadImpl]a.plus([CtVariableReadImpl]b));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getOverheadExpenses() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().payForOverhead()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getTheoreticalPayroll([CtLiteralImpl]false).multipliedBy([CtLiteralImpl]0.05);
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeUnit([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]id);
        [CtForEachImpl][CtCommentImpl]// remove all parts for this unit as well
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p : [CtInvocationImpl][CtVariableReadImpl]unit.getParts()) [CtBlockImpl]{
            [CtInvocationImpl]removePart([CtVariableReadImpl]p);
        }
        [CtForEachImpl][CtCommentImpl]// remove any personnel from this unit
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl][CtVariableReadImpl]unit.getCrew()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]unit.remove([CtVariableReadImpl]p, [CtLiteralImpl]true);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Person tech = [CtInvocationImpl][CtVariableReadImpl]unit.getTech();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]tech) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]unit.remove([CtVariableReadImpl]tech, [CtLiteralImpl]true);
        }
        [CtInvocationImpl][CtCommentImpl]// remove unit from any forces
        removeUnitFromForce([CtVariableReadImpl]unit);
        [CtInvocationImpl][CtCommentImpl]// If this is a ship, remove it from the list of potential transports
        removeTransportShip([CtVariableReadImpl]id);
        [CtInvocationImpl][CtCommentImpl]// finally remove the unit
        [CtInvocationImpl]getHangar().removeUnit([CtInvocationImpl][CtVariableReadImpl]unit.getId());
        [CtInvocationImpl]checkDuplicateNamesDuringDelete([CtInvocationImpl][CtVariableReadImpl]unit.getEntity());
        [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getName() + [CtLiteralImpl]" has been removed from the unit roster.");
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.UnitRemovedEvent([CtVariableReadImpl]unit));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removePerson([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id) [CtBlockImpl]{
        [CtInvocationImpl]removePerson([CtVariableReadImpl]id, [CtLiteralImpl]true);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removePerson([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id, [CtParameterImpl][CtTypeReferenceImpl]boolean log) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Person person = [CtInvocationImpl]getPerson([CtVariableReadImpl]id);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]person == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getGenealogy().clearGenealogy([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtInvocationImpl][CtVariableReadImpl]person.getUnitId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]u) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]u.remove([CtVariableReadImpl]person, [CtLiteralImpl]true);
        }
        [CtInvocationImpl]removeAllPatientsFor([CtVariableReadImpl]person);
        [CtInvocationImpl]removeAllTechJobsFor([CtVariableReadImpl]person);
        [CtInvocationImpl]removeKillsFor([CtInvocationImpl][CtVariableReadImpl]person.getId());
        [CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().removePerson([CtVariableReadImpl]person);
        [CtIfImpl]if ([CtVariableReadImpl]log) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person.getFullTitle() + [CtLiteralImpl]" has been removed from the personnel roster.");
        }
        [CtInvocationImpl][CtFieldReadImpl]personnel.remove([CtVariableReadImpl]id);
        [CtIfImpl][CtCommentImpl]// Deal with Astech Pool Minutes
        if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person.getPrimaryRole() == [CtFieldReadImpl]Person.T_ASTECH) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtFieldReadImpl]astechPoolMinutes - [CtLiteralImpl]480);
            [CtAssignmentImpl][CtFieldWriteImpl]astechPoolOvertime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtFieldReadImpl]astechPoolOvertime - [CtLiteralImpl]240);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]person.getSecondaryRole() == [CtFieldReadImpl]Person.T_ASTECH) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtFieldReadImpl]astechPoolMinutes - [CtLiteralImpl]240);
            [CtAssignmentImpl][CtFieldWriteImpl]astechPoolOvertime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtFieldReadImpl]astechPoolOvertime - [CtLiteralImpl]120);
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PersonRemovedEvent([CtVariableReadImpl]person));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void awardTrainingXP([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Lance l) [CtBlockImpl]{
        [CtInvocationImpl]awardTrainingXPByMaximumRole([CtVariableReadImpl]l);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Awards XP to the lance based on the maximum experience level of its
     * commanding officer and the minimum experience level of the unit's
     * members.
     *
     * @param l
     * 		The {@link Lance} to calculate XP to award for training.
     */
    private [CtTypeReferenceImpl]void awardTrainingXPByMaximumRole([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Lance l) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID trainerId : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtInvocationImpl][CtVariableReadImpl]l.getForceId()).getAllUnits([CtLiteralImpl]true)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit trainerUnit = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]trainerId);
            [CtIfImpl][CtCommentImpl]// not sure how this occurs, but it probably shouldn't halt processing of a new day.
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]trainerUnit == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Person commander = [CtInvocationImpl][CtVariableReadImpl]trainerUnit.getCommander();
            [CtIfImpl][CtCommentImpl]// AtB 2.31: Training lance – needs a officer with Veteran skill levels
            [CtCommentImpl]// and adds 1xp point to every Green skilled unit.
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]commander != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]commander.getRank().isOfficer()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// Take the maximum of the commander's Primary and Secondary Role
                [CtCommentImpl]// experience to calculate their experience level...
                [CtTypeReferenceImpl]int commanderExperience = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtInvocationImpl][CtVariableReadImpl]commander.getExperienceLevel([CtLiteralImpl]false), [CtInvocationImpl][CtVariableReadImpl]commander.getExperienceLevel([CtLiteralImpl]true));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]commanderExperience > [CtFieldReadImpl]SkillType.EXP_REGULAR) [CtBlockImpl]{
                    [CtForEachImpl][CtCommentImpl]// ...and if the commander is better than a veteran, find all of
                    [CtCommentImpl]// the personnel under their command...
                    for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID traineeId : [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]forceIds.get([CtInvocationImpl][CtVariableReadImpl]l.getForceId()).getAllUnits([CtLiteralImpl]true)) [CtBlockImpl]{
                        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit traineeUnit = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]traineeId);
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]traineeUnit == [CtLiteralImpl]null) [CtBlockImpl]{
                            [CtContinueImpl]continue;
                        }
                        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl][CtVariableReadImpl]traineeUnit.getCrew()) [CtBlockImpl]{
                            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.equals([CtVariableReadImpl]commander)) [CtBlockImpl]{
                                [CtContinueImpl]continue;
                            }
                            [CtLocalVariableImpl][CtCommentImpl]// ...and if their weakest role is Green or Ultra-Green
                            [CtTypeReferenceImpl]int experienceLevel = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl][CtVariableReadImpl]p.getExperienceLevel([CtLiteralImpl]false), [CtConditionalImpl][CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getSecondaryRole() != [CtFieldReadImpl]Person.T_NONE ? [CtInvocationImpl][CtVariableReadImpl]p.getExperienceLevel([CtLiteralImpl]true) : [CtFieldReadImpl]SkillType.EXP_ELITE);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]experienceLevel >= [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]experienceLevel < [CtFieldReadImpl]SkillType.EXP_REGULAR)) [CtBlockImpl]{
                                [CtInvocationImpl][CtCommentImpl]// ...add one XP.
                                [CtVariableReadImpl]p.awardXP([CtLiteralImpl]1);
                                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getHyperlinkedName() + [CtLiteralImpl]" has gained 1 XP from training.");
                            }
                        }
                    }
                    [CtBreakImpl]break;
                }
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeAllPatientsFor([CtParameterImpl][CtTypeReferenceImpl]Person doctor) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]p.getDoctorId()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getDoctorId().equals([CtInvocationImpl][CtVariableReadImpl]doctor.getId())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]p.setDoctorId([CtLiteralImpl]null, [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getNaturalHealingWaitingPeriod());
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeAllTechJobsFor([CtParameterImpl][CtTypeReferenceImpl]Person tech) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]tech == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getId() == [CtLiteralImpl]null)) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtInvocationImpl]getHangar().forEachUnit([CtLambdaImpl]([CtParameterImpl] u) -> [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tech.getId().equals([CtInvocationImpl][CtVariableReadImpl]u.getTechId())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]u.removeTech();
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]u.getRefit() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tech.getId().equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getRefit().getTeamId())) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getRefit().setTeamId([CtLiteralImpl]null);
            }
        });
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p : [CtInvocationImpl]getParts()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tech.getId().equals([CtInvocationImpl][CtVariableReadImpl]p.getTeamId())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]p.setTeamId([CtLiteralImpl]null);
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force f : [CtInvocationImpl][CtFieldReadImpl]forceIds.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]tech.getId().equals([CtInvocationImpl][CtVariableReadImpl]f.getTechID())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]f.setTechID([CtLiteralImpl]null);
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeScenario([CtParameterImpl][CtTypeReferenceImpl]int id) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Scenario scenario = [CtInvocationImpl]getScenario([CtVariableReadImpl]id);
        [CtInvocationImpl][CtVariableReadImpl]scenario.clearAllForcesAndPersonnel([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission mission = [CtInvocationImpl]getMission([CtInvocationImpl][CtVariableReadImpl]scenario.getMissionId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]mission) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]mission.removeScenario([CtInvocationImpl][CtVariableReadImpl]scenario.getId());
        }
        [CtInvocationImpl][CtFieldReadImpl]scenarios.remove([CtVariableReadImpl]id);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.ScenarioRemovedEvent([CtVariableReadImpl]scenario));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeMission([CtParameterImpl][CtTypeReferenceImpl]int id) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission mission = [CtInvocationImpl]getMission([CtVariableReadImpl]id);
        [CtIfImpl][CtCommentImpl]// Loop through scenarios here! We need to remove them as well.
        if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]mission) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Scenario scenario : [CtInvocationImpl][CtVariableReadImpl]mission.getScenarios()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]scenario.clearAllForcesAndPersonnel([CtThisAccessImpl]this);
                [CtInvocationImpl][CtFieldReadImpl]scenarios.remove([CtInvocationImpl][CtVariableReadImpl]scenario.getId());
            }
            [CtInvocationImpl][CtVariableReadImpl]mission.clearScenarios();
        }
        [CtInvocationImpl][CtFieldReadImpl]missions.remove([CtVariableReadImpl]id);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.MissionRemovedEvent([CtVariableReadImpl]mission));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removePart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]part.getUnit()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]part.getUnit() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.unit.TestUnit)) [CtBlockImpl]{
            [CtReturnImpl][CtCommentImpl]// if this is a test unit, then we won't remove the part because its not there
            return;
        }
        [CtInvocationImpl][CtFieldReadImpl]parts.remove([CtInvocationImpl][CtVariableReadImpl]part.getId());
        [CtForEachImpl][CtCommentImpl]// remove child parts as well
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part childPart : [CtInvocationImpl][CtVariableReadImpl]part.getChildParts()) [CtBlockImpl]{
            [CtInvocationImpl]removePart([CtVariableReadImpl]childPart);
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartRemovedEvent([CtVariableReadImpl]part));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeKill([CtParameterImpl][CtTypeReferenceImpl]Kill k) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]kills.containsKey([CtInvocationImpl][CtVariableReadImpl]k.getPilotId())) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]kills.get([CtInvocationImpl][CtVariableReadImpl]k.getPilotId()).remove([CtVariableReadImpl]k);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeKillsFor([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID personID) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]kills.remove([CtVariableReadImpl]personID);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeForce([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force force) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int fid = [CtInvocationImpl][CtVariableReadImpl]force.getId();
        [CtInvocationImpl][CtFieldReadImpl]forceIds.remove([CtVariableReadImpl]fid);
        [CtForEachImpl][CtCommentImpl]// clear forceIds of all personnel with this force
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID uid : [CtInvocationImpl][CtVariableReadImpl]force.getUnits()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]uid);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]u) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getForceId() == [CtVariableReadImpl]fid) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]u.setForceId([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]force.isDeployed()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]u.setScenarioId([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
                }
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.OrganizationChangedEvent([CtVariableReadImpl]force));
        [CtIfImpl][CtCommentImpl]// also remove this force's id from any scenarios
        if ([CtInvocationImpl][CtVariableReadImpl]force.isDeployed()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Scenario s = [CtInvocationImpl]getScenario([CtInvocationImpl][CtVariableReadImpl]force.getScenarioId());
            [CtInvocationImpl][CtVariableReadImpl]s.removeForce([CtVariableReadImpl]fid);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]campaignOptions.getUseAtB()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]lances.remove([CtVariableReadImpl]fid);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]force.getParentForce()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]force.getParentForce().removeSubForce([CtVariableReadImpl]fid);
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.force.Force> subs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtVariableReadImpl]force.getSubForces());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force sub : [CtVariableReadImpl]subs) [CtBlockImpl]{
            [CtInvocationImpl]removeForce([CtVariableReadImpl]sub);
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.OrganizationChangedEvent([CtVariableReadImpl]sub));
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeUnitFromForce([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force force = [CtInvocationImpl]getForce([CtInvocationImpl][CtVariableReadImpl]u.getForceId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]force) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]force.removeUnit([CtInvocationImpl][CtVariableReadImpl]u.getId());
            [CtInvocationImpl][CtVariableReadImpl]u.setForceId([CtTypeAccessImpl]Force.FORCE_NONE);
            [CtInvocationImpl][CtVariableReadImpl]u.setScenarioId([CtUnaryOperatorImpl]-[CtLiteralImpl]1);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasNavalC3() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().calculateFreeC3Nodes() < [CtLiteralImpl]5)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> removedUnits = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
                [CtInvocationImpl][CtVariableReadImpl]removedUnits.add([CtVariableReadImpl]u);
                [CtInvocationImpl]removeUnitsFromNetwork([CtVariableReadImpl]removedUnits);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().setC3MasterIsUUIDAsString([CtLiteralImpl]null);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().setC3Master([CtLiteralImpl]null, [CtLiteralImpl]true);
                [CtInvocationImpl]refreshNetworks();
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasC3i() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().calculateFreeC3Nodes() < [CtLiteralImpl]5)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> removedUnits = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
                [CtInvocationImpl][CtVariableReadImpl]removedUnits.add([CtVariableReadImpl]u);
                [CtInvocationImpl]removeUnitsFromNetwork([CtVariableReadImpl]removedUnits);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().setC3MasterIsUUIDAsString([CtLiteralImpl]null);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().setC3Master([CtLiteralImpl]null, [CtLiteralImpl]true);
                [CtInvocationImpl]refreshNetworks();
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasC3M()) [CtBlockImpl]{
                [CtInvocationImpl]removeUnitsFromC3Master([CtVariableReadImpl]u);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().setC3MasterIsUUIDAsString([CtLiteralImpl]null);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().setC3Master([CtLiteralImpl]null, [CtLiteralImpl]true);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]campaignOptions.getUseAtB() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]force.getUnits().size() == [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]lances.remove([CtInvocationImpl][CtVariableReadImpl]force.getId());
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.force.Force getForceFor([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getForce([CtInvocationImpl][CtVariableReadImpl]u.getForceId());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.force.Force getForceFor([CtParameterImpl][CtTypeReferenceImpl]Person p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtInvocationImpl][CtVariableReadImpl]p.getUnitId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]u != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getForceFor([CtVariableReadImpl]u);
        } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isTech()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force force : [CtInvocationImpl][CtFieldReadImpl]forceIds.values()) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getId().equals([CtInvocationImpl][CtVariableReadImpl]force.getTechID())) [CtBlockImpl]{
                    [CtReturnImpl]return [CtVariableReadImpl]force;
                }
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void restore() [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// if we fail to restore equipment parts then remove them
        [CtCommentImpl]// and possibly re-initialize and diagnose unit
        [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.parts.Part> partsToRemove = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.util.UUID> unitsToCheck = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part : [CtInvocationImpl]getParts()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) (part)).restore();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) (part)).getType()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]partsToRemove.add([CtVariableReadImpl]part);
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) (part)).restore();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) (part)).getType()) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]partsToRemove.add([CtVariableReadImpl]part);
                }
            }
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part remove : [CtVariableReadImpl]partsToRemove) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]remove.getUnitId()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]unitsToCheck.contains([CtInvocationImpl][CtVariableReadImpl]remove.getUnitId()))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]unitsToCheck.add([CtInvocationImpl][CtVariableReadImpl]remove.getUnitId());
            }
            [CtInvocationImpl]removePart([CtVariableReadImpl]remove);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]unit.getEntity()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().setOwner([CtFieldReadImpl]player);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().setGame([CtFieldReadImpl]game);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().restore();
                [CtIfImpl][CtCommentImpl]// Aerospace parts have changed after 0.45.4. Reinitialize parts for Small Craft and up
                if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_JUMPSHIP) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]unitsToCheck.add([CtInvocationImpl][CtVariableReadImpl]unit.getId());
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]unit.resetEngineer();
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID uid : [CtVariableReadImpl]unitsToCheck) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]uid);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]u) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]u.initializeParts([CtLiteralImpl]true);
                [CtInvocationImpl][CtVariableReadImpl]u.runDiagnostic([CtLiteralImpl]false);
            }
        }
        [CtInvocationImpl][CtFieldReadImpl]shoppingList.restore();
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUseAtB()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.RandomFactionGenerator.getInstance().startup([CtThisAccessImpl]this);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int loops = [CtLiteralImpl]0;
            [CtWhileImpl]while ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.generator.RandomUnitGenerator.getInstance().isInitialized()) [CtBlockImpl]{
                [CtTryImpl]try [CtBlockImpl]{
                    [CtInvocationImpl][CtTypeAccessImpl]java.lang.Thread.sleep([CtLiteralImpl]50);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](++[CtVariableWriteImpl]loops) > [CtLiteralImpl]20) [CtBlockImpl]{
                        [CtBreakImpl][CtCommentImpl]// Wait for up to a second
                        break;
                    }
                }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.InterruptedException ignore) [CtBlockImpl]{
                }
            } 
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Cleans incongruent data present in the campaign
     */
    public [CtTypeReferenceImpl]void cleanUp() [CtBlockImpl]{
        [CtForEachImpl][CtCommentImpl]// Cleans non-existing spouses
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl][CtFieldReadImpl]personnel.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getGenealogy().hasSpouse()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]personnel.containsKey([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getGenealogy().getSpouseId())) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getGenealogy().setSpouse([CtLiteralImpl]null);
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getKeepMarriedNameUponSpouseDeath()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getMaidenName() != [CtLiteralImpl]null)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]p.setSurname([CtInvocationImpl][CtVariableReadImpl]p.getMaidenName());
                    }
                    [CtInvocationImpl][CtVariableReadImpl]p.setMaidenName([CtLiteralImpl]null);
                }
            }
        }
        [CtForEachImpl][CtCommentImpl]// clean up non-existent unit references in force unit lists
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Force force : [CtInvocationImpl][CtFieldReadImpl]forceIds.values()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.util.UUID> orphanForceUnitIDs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID unitID : [CtInvocationImpl][CtVariableReadImpl]force.getUnits()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]unitID) == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]orphanForceUnitIDs.add([CtVariableReadImpl]unitID);
                }
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID unitID : [CtVariableReadImpl]orphanForceUnitIDs) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]force.removeUnit([CtVariableReadImpl]unitID);
            }
        }
        [CtForEachImpl][CtCommentImpl]// clean up units that are assigned to non-existing scenarios
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl][CtThisAccessImpl]this.getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtThisAccessImpl]this.getScenario([CtInvocationImpl][CtVariableReadImpl]unit.getScenarioId()) == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]unit.setScenarioId([CtTypeAccessImpl]Scenario.S_DEFAULT_ID);
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isOvertimeAllowed() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]overtime;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setOvertime([CtParameterImpl][CtTypeReferenceImpl]boolean b) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.overtime = [CtVariableReadImpl]b;
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.OvertimeModeEvent([CtVariableReadImpl]b));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isGM() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]gmMode;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setGMMode([CtParameterImpl][CtTypeReferenceImpl]boolean b) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.gmMode = [CtVariableReadImpl]b;
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.GMModeEvent([CtVariableReadImpl]b));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.universe.Faction getFaction() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Faction.getFaction([CtFieldReadImpl]factionCode);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getFactionName() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getFaction().getFullName([CtInvocationImpl]getGameYear());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setFactionCode([CtParameterImpl][CtTypeReferenceImpl]java.lang.String i) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.factionCode = [CtVariableReadImpl]i;
        [CtInvocationImpl]updateTechFactionCode();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getFactionCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]factionCode;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getRetainerEmployerCode() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]retainerEmployerCode;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRetainerEmployerCode([CtParameterImpl][CtTypeReferenceImpl]java.lang.String code) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]retainerEmployerCode = [CtVariableReadImpl]code;
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addInMemoryLogHistory([CtParameterImpl][CtTypeReferenceImpl]LogEntry le) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]inMemoryLogHistory.size() != [CtLiteralImpl]0) [CtBlockImpl]{
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.time.temporal.ChronoUnit.[CtFieldReferenceImpl]DAYS.between([CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]inMemoryLogHistory.get([CtLiteralImpl]0).getDate(), [CtInvocationImpl][CtVariableReadImpl]le.getDate()) > [CtFieldReadImpl]MekHqConstants.MAX_HISTORICAL_LOG_DAYS) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// we've hit the max size for the in-memory based on the UI display limit prune the oldest entry
                [CtFieldReadImpl]inMemoryLogHistory.remove([CtLiteralImpl]0);
            } 
        }
        [CtInvocationImpl][CtFieldReadImpl]inMemoryLogHistory.add([CtVariableReadImpl]le);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Starts a new day for the daily log
     *
     * @param r
     * 		- the report String
     */
    public [CtTypeReferenceImpl]void beginReport([CtParameterImpl][CtTypeReferenceImpl]java.lang.String r) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getMekHQOptions().getHistoricalDailyLog()) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// add the new items to our in-memory cache
            addInMemoryLogHistory([CtConstructorCallImpl]new [CtTypeReferenceImpl]HistoricalLogEntry([CtInvocationImpl]getLocalDate(), [CtLiteralImpl]""));
        }
        [CtInvocationImpl]addReportInternal([CtVariableReadImpl]r);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Adds a report to the daily log
     *
     * @param r
     * 		- the report String
     */
    public [CtTypeReferenceImpl]void addReport([CtParameterImpl][CtTypeReferenceImpl]java.lang.String r) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getMekHQOptions().getHistoricalDailyLog()) [CtBlockImpl]{
            [CtInvocationImpl]addInMemoryLogHistory([CtConstructorCallImpl]new [CtTypeReferenceImpl]HistoricalLogEntry([CtInvocationImpl]getLocalDate(), [CtVariableReadImpl]r));
        }
        [CtInvocationImpl]addReportInternal([CtVariableReadImpl]r);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void addReportInternal([CtParameterImpl][CtTypeReferenceImpl]java.lang.String r) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]currentReport.add([CtVariableReadImpl]r);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]currentReportHTML.length() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]currentReportHTML = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]currentReportHTML + [CtFieldReadImpl]mekhq.campaign.Campaign.REPORT_LINEBREAK) + [CtVariableReadImpl]r;
            [CtInvocationImpl][CtFieldReadImpl]newReports.add([CtFieldReadImpl]mekhq.campaign.Campaign.REPORT_LINEBREAK);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]currentReportHTML = [CtVariableReadImpl]r;
        }
        [CtInvocationImpl][CtFieldReadImpl]newReports.add([CtVariableReadImpl]r);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.ReportEvent([CtThisAccessImpl]this, [CtVariableReadImpl]r));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addReports([CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]java.lang.String> reports) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String r : [CtVariableReadImpl]reports) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtVariableReadImpl]r);
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setCamoCategory([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]camoCategory = [CtVariableReadImpl]name;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCamoCategory() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]camoCategory;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setCamoFileName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]camoFileName = [CtVariableReadImpl]name;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCamoFileName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]camoFileName;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getColorIndex() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]colorIndex;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setColorIndex([CtParameterImpl][CtTypeReferenceImpl]int index) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]colorIndex = [CtVariableReadImpl]index;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getIconCategory() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]iconCategory;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setIconCategory([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.iconCategory = [CtVariableReadImpl]s;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getIconFileName() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]iconFileName;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setIconFileName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String s) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.iconFileName = [CtVariableReadImpl]s;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.parts.Part> getSpareParts() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.parts.Part> spares = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part : [CtInvocationImpl]getParts()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]part.isSpare()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]spares.add([CtVariableReadImpl]part);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]spares;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Finds the first spare part matching a predicate.
     *
     * @param predicate
     * 		The predicate to use when searching
     * 		for a suitable spare part.
     * @return A matching spare {@link Part} or {@code null}
    if no suitable match was found.
     */
    [CtAnnotationImpl]@megamek.common.annotations.Nullable
    public [CtTypeReferenceImpl]mekhq.campaign.parts.Part findSparePart([CtParameterImpl][CtTypeReferenceImpl]java.util.function.Predicate<[CtTypeReferenceImpl]mekhq.campaign.parts.Part> predicate) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part : [CtInvocationImpl][CtFieldReadImpl]parts.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]part.isSpare() && [CtInvocationImpl][CtVariableReadImpl]predicate.test([CtVariableReadImpl]part)) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]part;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Streams the spare parts in the campaign.
     *
     * @return A stream of spare parts in the campaign.
     */
    public [CtTypeReferenceImpl]java.util.stream.Stream<[CtTypeReferenceImpl]mekhq.campaign.parts.Part> streamSpareParts() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]parts.values().stream().filter([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Part::isSpare);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addFunds([CtParameterImpl][CtTypeReferenceImpl]Money quantity) [CtBlockImpl]{
        [CtInvocationImpl]addFunds([CtVariableReadImpl]quantity, [CtLiteralImpl]"Rich Uncle", [CtTypeAccessImpl]Transaction.C_MISC);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addFunds([CtParameterImpl][CtTypeReferenceImpl]Money quantity, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String description, [CtParameterImpl][CtTypeReferenceImpl]int category) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]description == [CtLiteralImpl]null) || [CtInvocationImpl][CtVariableReadImpl]description.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]description = [CtLiteralImpl]"Rich Uncle";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]category == [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]category = [CtFieldReadImpl]Transaction.C_MISC;
        }
        [CtInvocationImpl][CtFieldReadImpl]finances.credit([CtVariableReadImpl]quantity, [CtVariableReadImpl]category, [CtVariableReadImpl]description, [CtInvocationImpl]getLocalDate());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String quantityString = [CtInvocationImpl][CtVariableReadImpl]quantity.toAmountAndSymbolString();
        [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Funds added : " + [CtVariableReadImpl]quantityString) + [CtLiteralImpl]" (") + [CtVariableReadImpl]description) + [CtLiteralImpl]")");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean hasEnoughFunds([CtParameterImpl][CtTypeReferenceImpl]Money cost) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getFunds().isGreaterOrEqualThan([CtVariableReadImpl]cost);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean buyUnit([CtParameterImpl][CtTypeReferenceImpl]Entity en, [CtParameterImpl][CtTypeReferenceImpl]int days) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money cost = [CtInvocationImpl][CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtVariableReadImpl]en, [CtThisAccessImpl]this).getBuyCost();
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]campaignOptions.payForUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]finances.debit([CtVariableReadImpl]cost, [CtTypeAccessImpl]Transaction.C_UNIT, [CtBinaryOperatorImpl][CtLiteralImpl]"Purchased " + [CtInvocationImpl][CtVariableReadImpl]en.getShortName(), [CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
                [CtInvocationImpl]addNewUnit([CtVariableReadImpl]en, [CtLiteralImpl]false, [CtVariableReadImpl]days);
                [CtReturnImpl]return [CtLiteralImpl]true;
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        } else [CtBlockImpl]{
            [CtInvocationImpl]addNewUnit([CtVariableReadImpl]en, [CtLiteralImpl]false, [CtVariableReadImpl]days);
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void sellUnit([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID id) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]id);
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money sellValue = [CtInvocationImpl][CtVariableReadImpl]unit.getSellValue();
        [CtInvocationImpl][CtFieldReadImpl]finances.credit([CtVariableReadImpl]sellValue, [CtTypeAccessImpl]Transaction.C_UNIT_SALE, [CtBinaryOperatorImpl][CtLiteralImpl]"Sale of " + [CtInvocationImpl][CtVariableReadImpl]unit.getName(), [CtInvocationImpl]getLocalDate());
        [CtInvocationImpl]removeUnit([CtVariableReadImpl]id);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.UnitRemovedEvent([CtVariableReadImpl]unit));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void sellPart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part, [CtParameterImpl][CtTypeReferenceImpl]int quantity) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
            [CtInvocationImpl]sellAmmo([CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (part)), [CtVariableReadImpl]quantity);
            [CtReturnImpl]return;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
            [CtInvocationImpl]sellArmor([CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (part)), [CtVariableReadImpl]quantity);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money cost = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]part.getActualValue().multipliedBy([CtVariableReadImpl]quantity);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String plural = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]quantity > [CtLiteralImpl]1) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]plural = [CtLiteralImpl]"s";
        }
        [CtInvocationImpl][CtFieldReadImpl]finances.credit([CtVariableReadImpl]cost, [CtTypeAccessImpl]Transaction.C_EQUIP_SALE, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Sale of " + [CtVariableReadImpl]quantity) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]part.getName()) + [CtVariableReadImpl]plural, [CtInvocationImpl]getLocalDate());
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]quantity > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]part.getQuantity() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]part.decrementQuantity();
            [CtUnaryOperatorImpl][CtVariableWriteImpl]quantity--;
        } 
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartRemovedEvent([CtVariableReadImpl]part));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void sellAmmo([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage ammo, [CtParameterImpl][CtTypeReferenceImpl]int shots) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]shots = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]shots, [CtInvocationImpl][CtVariableReadImpl]ammo.getShots());
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean sellingAllAmmo = [CtBinaryOperatorImpl][CtVariableReadImpl]shots == [CtInvocationImpl][CtVariableReadImpl]ammo.getShots();
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money cost = [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ammo.getShots() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]cost = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ammo.getActualValue().multipliedBy([CtVariableReadImpl]shots).dividedBy([CtInvocationImpl][CtVariableReadImpl]ammo.getShots());
        }
        [CtInvocationImpl][CtFieldReadImpl]finances.credit([CtVariableReadImpl]cost, [CtTypeAccessImpl]Transaction.C_EQUIP_SALE, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Sale of " + [CtVariableReadImpl]shots) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]ammo.getName(), [CtInvocationImpl]getLocalDate());
        [CtIfImpl]if ([CtVariableReadImpl]sellingAllAmmo) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ammo.decrementQuantity();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]ammo.changeShots([CtBinaryOperatorImpl][CtUnaryOperatorImpl](-[CtLiteralImpl]1) * [CtVariableReadImpl]shots);
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartRemovedEvent([CtVariableReadImpl]ammo));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void sellArmor([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor armor, [CtParameterImpl][CtTypeReferenceImpl]int points) [CtBlockImpl]{
        [CtAssignmentImpl][CtVariableWriteImpl]points = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]points, [CtInvocationImpl][CtVariableReadImpl]armor.getAmount());
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean sellingAllArmor = [CtBinaryOperatorImpl][CtVariableReadImpl]points == [CtInvocationImpl][CtVariableReadImpl]armor.getAmount();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double proportion = [CtBinaryOperatorImpl][CtVariableReadImpl](([CtTypeReferenceImpl]double) (points)) / [CtInvocationImpl][CtVariableReadImpl]armor.getAmount();
        [CtIfImpl]if ([CtVariableReadImpl]sellingAllArmor) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// to avoid rounding error
            [CtVariableWriteImpl]proportion = [CtLiteralImpl]1.0;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money cost = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]armor.getActualValue().multipliedBy([CtVariableReadImpl]proportion);
        [CtInvocationImpl][CtFieldReadImpl]finances.credit([CtVariableReadImpl]cost, [CtTypeAccessImpl]Transaction.C_EQUIP_SALE, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Sale of " + [CtVariableReadImpl]points) + [CtLiteralImpl]" ") + [CtInvocationImpl][CtVariableReadImpl]armor.getName(), [CtInvocationImpl]getLocalDate());
        [CtIfImpl]if ([CtVariableReadImpl]sellingAllArmor) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]armor.decrementQuantity();
        } else [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]armor.changeAmountAvailable([CtBinaryOperatorImpl][CtUnaryOperatorImpl](-[CtLiteralImpl]1) * [CtVariableReadImpl]points);
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartRemovedEvent([CtVariableReadImpl]armor));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void depodPart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part, [CtParameterImpl][CtTypeReferenceImpl]int quantity) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part unpodded = [CtInvocationImpl][CtVariableReadImpl]part.clone();
        [CtInvocationImpl][CtVariableReadImpl]unpodded.setOmniPodded([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.OmniPod pod = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.parts.OmniPod([CtVariableReadImpl]unpodded, [CtThisAccessImpl]this);
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]quantity > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]part.getQuantity() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl]addPart([CtInvocationImpl][CtVariableReadImpl]unpodded.clone(), [CtLiteralImpl]0);
            [CtInvocationImpl]addPart([CtInvocationImpl][CtVariableReadImpl]pod.clone(), [CtLiteralImpl]0);
            [CtInvocationImpl][CtVariableReadImpl]part.decrementQuantity();
            [CtUnaryOperatorImpl][CtVariableWriteImpl]quantity--;
        } 
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartRemovedEvent([CtVariableReadImpl]part));
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartRemovedEvent([CtVariableReadImpl]pod));
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartRemovedEvent([CtVariableReadImpl]unpodded));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean buyRefurbishment([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().payForParts()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]finances.debit([CtInvocationImpl][CtVariableReadImpl]part.getStickerPrice(), [CtTypeAccessImpl]Transaction.C_EQUIP, [CtBinaryOperatorImpl][CtLiteralImpl]"Purchase of " + [CtInvocationImpl][CtVariableReadImpl]part.getName(), [CtInvocationImpl]getLocalDate());
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean buyPart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part, [CtParameterImpl][CtTypeReferenceImpl]int transitDays) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]buyPart([CtVariableReadImpl]part, [CtLiteralImpl]1, [CtVariableReadImpl]transitDays);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean buyPart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part, [CtParameterImpl][CtTypeReferenceImpl]double multiplier, [CtParameterImpl][CtTypeReferenceImpl]int transitDays) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().payForParts()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]finances.debit([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]part.getStickerPrice().multipliedBy([CtVariableReadImpl]multiplier), [CtTypeAccessImpl]Transaction.C_EQUIP, [CtBinaryOperatorImpl][CtLiteralImpl]"Purchase of " + [CtInvocationImpl][CtVariableReadImpl]part.getName(), [CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Refit) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Refit) (part)).addRefitKitParts([CtVariableReadImpl]transitDays);
                } else [CtBlockImpl]{
                    [CtInvocationImpl]addPart([CtVariableReadImpl]part, [CtVariableReadImpl]transitDays);
                }
                [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartNewEvent([CtVariableReadImpl]part));
                [CtReturnImpl]return [CtLiteralImpl]true;
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtLiteralImpl]false;
            }
        } else [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Refit) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Refit) (part)).addRefitKitParts([CtVariableReadImpl]transitDays);
            } else [CtBlockImpl]{
                [CtInvocationImpl]addPart([CtVariableReadImpl]part, [CtVariableReadImpl]transitDays);
            }
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PartNewEvent([CtVariableReadImpl]part));
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
    }

    [CtMethodImpl]public static [CtTypeReferenceImpl]mekhq.campaign.Entity getBrandNewUndamagedEntity([CtParameterImpl][CtTypeReferenceImpl]java.lang.String entityShortName) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]MechSummary mechSummary = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MechSummaryCache.getInstance().getMech([CtVariableReadImpl]entityShortName);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mechSummary == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]MechFileParser mechFileParser = [CtLiteralImpl]null;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]mechFileParser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MechFileParser([CtInvocationImpl][CtVariableReadImpl]mechSummary.getSourceFile());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]megamek.common.loaders.EntityLoadingException ex) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().error([CtFieldReadImpl]mekhq.campaign.Campaign.class, [CtLiteralImpl]"getBrandNewUndamagedEntity(String)", [CtVariableReadImpl]ex);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mechFileParser == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]mechFileParser.getEntity();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.CampaignOptions getCampaignOptions() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]campaignOptions;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setCampaignOptions([CtParameterImpl][CtTypeReferenceImpl]CampaignOptions options) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]campaignOptions = [CtVariableReadImpl]options;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void writeToXml([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter pw1) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int indent = [CtLiteralImpl]1;
        [CtInvocationImpl][CtCommentImpl]// File header
        [CtVariableReadImpl]pw1.println([CtLiteralImpl]"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ResourceBundle resourceMap = [CtInvocationImpl][CtTypeAccessImpl]java.util.ResourceBundle.getBundle([CtLiteralImpl]"mekhq.resources.MekHQ");
        [CtInvocationImpl][CtCommentImpl]// Start the XML root.
        [CtVariableReadImpl]pw1.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<campaign version=\"" + [CtInvocationImpl][CtVariableReadImpl]resourceMap.getString([CtLiteralImpl]"Application.version")) + [CtLiteralImpl]"\">");
        [CtInvocationImpl][CtCommentImpl]// region Basic Campaign Info
        [CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"info");
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"id", [CtInvocationImpl][CtFieldReadImpl]id.toString());
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"name", [CtFieldReadImpl]name);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"faction", [CtFieldReadImpl]factionCode);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]retainerEmployerCode != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"retainerEmployerCode", [CtFieldReadImpl]retainerEmployerCode);
        }
        [CtInvocationImpl][CtCommentImpl]// Ranks
        [CtFieldReadImpl]ranks.writeToXml([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"nameGen", [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.generator.RandomNameGenerator.getInstance().getChosenFaction());
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"percentFemale", [CtInvocationImpl][CtTypeAccessImpl]megamek.client.generator.RandomGenderGenerator.getPercentFemale());
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"overtime", [CtFieldReadImpl]overtime);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"gmMode", [CtFieldReadImpl]gmMode);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"astechPool", [CtFieldReadImpl]astechPool);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"astechPoolMinutes", [CtFieldReadImpl]astechPoolMinutes);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"astechPoolOvertime", [CtFieldReadImpl]astechPoolOvertime);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"medicPool", [CtFieldReadImpl]medicPool);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"camoCategory", [CtFieldReadImpl]camoCategory);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"camoFileName", [CtFieldReadImpl]camoFileName);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"iconCategory", [CtFieldReadImpl]iconCategory);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"iconFileName", [CtFieldReadImpl]iconFileName);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"colorIndex", [CtFieldReadImpl]colorIndex);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"lastPartId", [CtFieldReadImpl]lastPartId);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"lastForceId", [CtFieldReadImpl]lastForceId);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"lastMissionId", [CtFieldReadImpl]lastMissionId);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"lastScenarioId", [CtFieldReadImpl]lastScenarioId);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"calendar", [CtInvocationImpl][CtTypeAccessImpl]megamek.utils.MegaMekXmlUtil.saveFormattedDate([CtInvocationImpl]getLocalDate()));
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"fatigueLevel", [CtFieldReadImpl]fatigueLevel);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"nameGen");
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]2, [CtLiteralImpl]"faction", [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.generator.RandomNameGenerator.getInstance().getChosenFaction());
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]2, [CtLiteralImpl]"percentFemale", [CtInvocationImpl][CtTypeAccessImpl]megamek.client.generator.RandomGenderGenerator.getPercentFemale());
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"nameGen");
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"currentReport");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String s : [CtFieldReadImpl]currentReport) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// This cannot use the MekHQXMLUtil as it cannot be escaped
            [CtVariableReadImpl]pw1.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.indentStr([CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]2) + [CtLiteralImpl]"<reportLine><![CDATA[") + [CtVariableReadImpl]s) + [CtLiteralImpl]"]]></reportLine>");
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"currentReport");
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"info");
        [CtIfImpl][CtCommentImpl]// endregion Basic Campaign Info
        [CtCommentImpl]// region Campaign Options
        if ([CtBinaryOperatorImpl][CtInvocationImpl]getCampaignOptions() != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent);
        }
        [CtInvocationImpl][CtCommentImpl]// endregion Campaign Options
        [CtCommentImpl]// Lists of objects:
        [CtFieldReadImpl]units.writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"units");[CtCommentImpl]// Units

        [CtInvocationImpl]writeMapToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"personnel", [CtFieldReadImpl]personnel);[CtCommentImpl]// Personnel

        [CtInvocationImpl]writeMapToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"missions", [CtFieldReadImpl]missions);[CtCommentImpl]// Missions

        [CtInvocationImpl][CtCommentImpl]// the forces structure is hierarchical, but that should be handled
        [CtCommentImpl]// internally from with writeToXML function for Force
        [CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"forces");
        [CtInvocationImpl][CtFieldReadImpl]forces.writeToXml([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"forces");
        [CtInvocationImpl][CtFieldReadImpl]finances.writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent);
        [CtInvocationImpl][CtFieldReadImpl]location.writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent);
        [CtInvocationImpl][CtFieldReadImpl]shoppingList.writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent);
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"kills");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Kill> kills : [CtInvocationImpl][CtFieldReadImpl]kills.values()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Kill k : [CtVariableReadImpl]kills) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]k.writeToXml([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1);
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"kills");
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"skillTypes");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name : [CtFieldReadImpl]SkillType.skillList) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]SkillType type = [CtInvocationImpl][CtTypeAccessImpl]SkillType.getType([CtVariableReadImpl]name);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]type) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]type.writeToXml([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1);
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"skillTypes");
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"specialAbilities");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key : [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]SpecialAbility.getAllSpecialAbilities().keySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]SpecialAbility.getAbility([CtVariableReadImpl]key).writeToXml([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1);
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"specialAbilities");
        [CtInvocationImpl][CtFieldReadImpl]rskillPrefs.writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent);
        [CtInvocationImpl][CtCommentImpl]// parts is the biggest so it goes last
        writeMapToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"parts", [CtFieldReadImpl]parts);[CtCommentImpl]// Parts

        [CtInvocationImpl]writeGameOptions([CtVariableReadImpl]pw1);
        [CtInvocationImpl][CtCommentImpl]// Personnel Market
        [CtFieldReadImpl]personnelMarket.writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent);
        [CtIfImpl][CtCommentImpl]// Against the Bot
        if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUseAtB()) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]contractMarket.writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent);
            [CtInvocationImpl][CtFieldReadImpl]unitMarket.writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent);
            [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"colorIndex", [CtFieldReadImpl]colorIndex);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]lances.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"lances");
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.force.Lance l : [CtInvocationImpl][CtFieldReadImpl]lances.values()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]forceIds.containsKey([CtInvocationImpl][CtVariableReadImpl]l.getForceId())) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]l.writeToXml([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1);
                    }
                }
                [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"lances");
            }
            [CtInvocationImpl][CtFieldReadImpl]retirementDefectionTracker.writeToXml([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]shipSearchStart != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"shipSearchStart", [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.saveFormattedDate([CtInvocationImpl]getShipSearchStart()));
            }
            [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"shipSearchType", [CtFieldReadImpl]shipSearchType);
            [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"shipSearchResult", [CtFieldReadImpl]shipSearchResult);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]shipSearchExpiration != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"shipSearchExpiration", [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.saveFormattedDate([CtInvocationImpl]getShipSearchExpiration()));
            }
        }
        [CtInvocationImpl][CtCommentImpl]// Customised planetary events
        [CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"customPlanetaryEvents");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem psystem : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystems().values()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// first check for system-wide events
            [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem.PlanetarySystemEvent> customSysEvents = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem.PlanetarySystemEvent event : [CtInvocationImpl][CtVariableReadImpl]psystem.getEvents()) [CtBlockImpl]{
                [CtIfImpl]if ([CtFieldReadImpl][CtVariableReadImpl]event.custom) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]customSysEvents.add([CtVariableReadImpl]event);
                }
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean startedSystem = [CtLiteralImpl]false;
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]customSysEvents.isEmpty()) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"system");
                [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]2, [CtLiteralImpl]"id", [CtInvocationImpl][CtVariableReadImpl]psystem.getId());
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem.PlanetarySystemEvent event : [CtVariableReadImpl]customSysEvents) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().writePlanetarySystemEvent([CtVariableReadImpl]pw1, [CtVariableReadImpl]event);
                    [CtInvocationImpl][CtVariableReadImpl]pw1.println();
                }
                [CtAssignmentImpl][CtVariableWriteImpl]startedSystem = [CtLiteralImpl]true;
            }
            [CtForEachImpl][CtCommentImpl]// now check for planetary events
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.Planet p : [CtInvocationImpl][CtVariableReadImpl]psystem.getPlanets()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl][CtTypeReferenceImpl]mekhq.campaign.universe.Planet.PlanetaryEvent> customEvents = [CtInvocationImpl][CtVariableReadImpl]p.getCustomEvents();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]customEvents.isEmpty()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]startedSystem) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// only write this if we haven't already started the system
                        [CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"system");
                        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]2, [CtLiteralImpl]"id", [CtInvocationImpl][CtVariableReadImpl]psystem.getId());
                    }
                    [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLOpenIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]2, [CtLiteralImpl]"planet");
                    [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]3, [CtLiteralImpl]"sysPos", [CtInvocationImpl][CtVariableReadImpl]p.getSystemPosition());
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]mekhq.campaign.universe.Planet.PlanetaryEvent event : [CtVariableReadImpl]customEvents) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().writePlanetaryEvent([CtVariableReadImpl]pw1, [CtVariableReadImpl]event);
                        [CtInvocationImpl][CtVariableReadImpl]pw1.println();
                    }
                    [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]2, [CtLiteralImpl]"planet");
                    [CtAssignmentImpl][CtVariableWriteImpl]startedSystem = [CtLiteralImpl]true;
                }
            }
            [CtIfImpl]if ([CtVariableReadImpl]startedSystem) [CtBlockImpl]{
                [CtInvocationImpl][CtCommentImpl]// close the system
                [CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1, [CtLiteralImpl]"system");
            }
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtVariableReadImpl]indent, [CtLiteralImpl]"customPlanetaryEvents");
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getMekHQOptions().getWriteCustomsToXML()) [CtBlockImpl]{
            [CtInvocationImpl]writeCustoms([CtVariableReadImpl]pw1);
        }
        [CtInvocationImpl][CtCommentImpl]// Okay, we're done.
        [CtCommentImpl]// Close everything out and be done with it.
        [CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXMLCloseIndentedLine([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent - [CtLiteralImpl]1, [CtLiteralImpl]"campaign");
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void writeGameOptions([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter pw1) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtLiteralImpl]"\t<gameOptions>");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.options.IBasicOption option : [CtInvocationImpl]getGameOptionsVector()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtLiteralImpl]"\t\t<gameoption>");[CtCommentImpl]// $NON-NLS-1$

            [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtLiteralImpl]3, [CtLiteralImpl]"name", [CtInvocationImpl][CtVariableReadImpl]option.getName());
            [CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.writeSimpleXmlTag([CtVariableReadImpl]pw1, [CtLiteralImpl]3, [CtLiteralImpl]"value", [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]option.getValue().toString());
            [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtLiteralImpl]"\t\t</gameoption>");[CtCommentImpl]// $NON-NLS-1$

        }
        [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtLiteralImpl]"\t</gameOptions>");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * A helper function to encapsulate writing the array/hash pairs out to XML. Each of the types requires a different XML
     * structure, but is in an identical holding structure. Thus, genericized function and interface to cleanly wrap it up.
     * God, I love 3rd-generation programming languages.
     *
     * @param <arrType>
     * 		The object type in the list. Must implement MekHqXmlSerializable.
     * @param pw1
     * 		The PrintWriter to output XML to.
     * @param indent
     * 		The indentation level to use for writing XML (purely for neatness).
     * @param tag
     * 		The name of the tag to use to encapsulate it.
     * @param array
     * 		The list of objects to write out.
     * @param hashtab
     * 		The lookup hashtable for the associated array.
     */
    private <[CtTypeParameterImpl]arrType> [CtTypeReferenceImpl]void writeArrayAndHashToXml([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter pw1, [CtParameterImpl][CtTypeReferenceImpl]int indent, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tag, [CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeParameterReferenceImpl]arrType> array, [CtParameterImpl][CtTypeReferenceImpl]java.util.Hashtable<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeParameterReferenceImpl]arrType> hashtab) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Hooray for implicitly-type-detected genericized functions!
        [CtCommentImpl]// However, I still ended up making an interface to handle this.
        [CtCommentImpl]// That way, I can cast it and call "writeToXml" to make it cleaner.
        [CtVariableReadImpl]pw1.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.indentStr([CtVariableReadImpl]indent) + [CtLiteralImpl]"<") + [CtVariableReadImpl]tag) + [CtLiteralImpl]">");
        [CtForEachImpl][CtCommentImpl]// Enumeration<Integer> = hashtab.keys
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer x : [CtInvocationImpl][CtVariableReadImpl]hashtab.keySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]MekHqXmlSerializable) ([CtVariableReadImpl]hashtab.get([CtVariableReadImpl]x))).writeToXml([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1);
        }
        [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.indentStr([CtVariableReadImpl]indent) + [CtLiteralImpl]"</") + [CtVariableReadImpl]tag) + [CtLiteralImpl]">");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * A helper function to encapsulate writing the array/hash pairs out to XML. Each of the types requires a different XML
     * structure, but is in an identical holding structure. Thus, genericized function and interface to cleanly wrap it up.
     * God, I love 3rd-generation programming languages.
     *
     * @param <arrType>
     * 		The object type in the list. Must implement MekHqXmlSerializable.
     * @param pw1
     * 		The PrintWriter to output XML to.
     * @param indent
     * 		The indentation level to use for writing XML (purely for neatness).
     * @param tag
     * 		The name of the tag to use to encapsulate it.
     * @param array
     * 		The list of objects to write out.
     * @param hashtab
     * 		The lookup hashtable for the associated array.
     */
    private <[CtTypeParameterImpl]arrType> [CtTypeReferenceImpl]void writeArrayAndHashToXmlforUUID([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter pw1, [CtParameterImpl][CtTypeReferenceImpl]int indent, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tag, [CtParameterImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeParameterReferenceImpl]arrType> array, [CtParameterImpl][CtTypeReferenceImpl]java.util.Hashtable<[CtTypeReferenceImpl]java.util.UUID, [CtTypeParameterReferenceImpl]arrType> hashtab) [CtBlockImpl]{
        [CtInvocationImpl][CtCommentImpl]// Hooray for implicitly-type-detected genericized functions!
        [CtCommentImpl]// However, I still ended up making an interface to handle this.
        [CtCommentImpl]// That way, I can cast it and call "writeToXml" to make it cleaner.
        [CtVariableReadImpl]pw1.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.indentStr([CtVariableReadImpl]indent) + [CtLiteralImpl]"<") + [CtVariableReadImpl]tag) + [CtLiteralImpl]">");
        [CtForEachImpl][CtCommentImpl]// Enumeration<Integer> = hashtab.keys
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID x : [CtInvocationImpl][CtVariableReadImpl]hashtab.keySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl](([CtTypeReferenceImpl]MekHqXmlSerializable) ([CtVariableReadImpl]hashtab.get([CtVariableReadImpl]x))).writeToXml([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1);
        }
        [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.indentStr([CtVariableReadImpl]indent) + [CtLiteralImpl]"</") + [CtVariableReadImpl]tag) + [CtLiteralImpl]">");
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * A helper function to encapsulate writing the map entries out to XML.
     *
     * @param <keyType>
     * 		The key type of the map.
     * @param <valueType>
     * 		The object type of the map. Must implement MekHqXmlSerializable.
     * @param pw1
     * 		The PrintWriter to output XML to.
     * @param indent
     * 		The indentation level to use for writing XML (purely for neatness).
     * @param tag
     * 		The name of the tag to use to encapsulate it.
     * @param map
     * 		The map of objects to write out.
     */
    private <[CtTypeParameterImpl]keyType, [CtTypeParameterImpl]valueType extends [CtTypeReferenceImpl]mekhq.campaign.MekHqXmlSerializable> [CtTypeReferenceImpl]void writeMapToXml([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter pw1, [CtParameterImpl][CtTypeReferenceImpl]int indent, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String tag, [CtParameterImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeParameterReferenceImpl]keyType, [CtTypeParameterReferenceImpl]valueType> map) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.indentStr([CtVariableReadImpl]indent) + [CtLiteralImpl]"<") + [CtVariableReadImpl]tag) + [CtLiteralImpl]">");
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeParameterReferenceImpl]keyType, [CtTypeParameterReferenceImpl]valueType> x : [CtInvocationImpl][CtVariableReadImpl]map.entrySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]x.getValue().writeToXml([CtVariableReadImpl]pw1, [CtBinaryOperatorImpl][CtVariableReadImpl]indent + [CtLiteralImpl]1);
        }
        [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtTypeAccessImpl]MekHqXmlUtil.indentStr([CtVariableReadImpl]indent) + [CtLiteralImpl]"</") + [CtVariableReadImpl]tag) + [CtLiteralImpl]">");
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]void writeCustoms([CtParameterImpl][CtTypeReferenceImpl]java.io.PrintWriter pw1) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name : [CtFieldReadImpl]customs) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]MechSummary ms = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MechSummaryCache.getInstance().getMech([CtVariableReadImpl]name);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ms == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]MechFileParser mechFileParser = [CtLiteralImpl]null;
            [CtTryImpl]try [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]mechFileParser = [CtConstructorCallImpl]new [CtTypeReferenceImpl]MechFileParser([CtInvocationImpl][CtVariableReadImpl]ms.getSourceFile());
            }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]megamek.common.loaders.EntityLoadingException ex) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().error([CtFieldReadImpl]mekhq.campaign.Campaign.class, [CtLiteralImpl]"writeCustoms(PrintWriter)", [CtVariableReadImpl]ex);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mechFileParser == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]mechFileParser.getEntity();
            [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtLiteralImpl]"\t<custom>");
            [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"\t\t<name>" + [CtVariableReadImpl]name) + [CtLiteralImpl]"</name>");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Mech) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]pw1.print([CtLiteralImpl]"\t\t<mtf><![CDATA[");
                [CtInvocationImpl][CtVariableReadImpl]pw1.print([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]Mech) (en)).getMtf());
                [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtLiteralImpl]"]]></mtf>");
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]pw1.print([CtLiteralImpl]"\t\t<blk><![CDATA[");
                [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.util.BuildingBlock blk = [CtInvocationImpl][CtTypeAccessImpl]megamek.common.loaders.BLKFile.getBlock([CtVariableReadImpl]en);
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String s : [CtInvocationImpl][CtVariableReadImpl]blk.getAllDataAsString()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]s.isEmpty()) [CtBlockImpl]{
                        [CtContinueImpl]continue;
                    }
                    [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtVariableReadImpl]s);
                }
                [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtLiteralImpl]"]]></blk>");
            }
            [CtInvocationImpl][CtVariableReadImpl]pw1.println([CtLiteralImpl]"\t</custom>");
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem> getSystems() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem> systems = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystems().keySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]systems.add([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystems().get([CtVariableReadImpl]key));
        }
        [CtReturnImpl]return [CtVariableReadImpl]systems;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem getSystemById([CtParameterImpl][CtTypeReferenceImpl]java.lang.String id) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystemById([CtVariableReadImpl]id);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]java.lang.String> getSystemNames() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]java.lang.String> systemNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem key : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystems().values()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]systemNames.add([CtInvocationImpl][CtVariableReadImpl]key.getPrintableName([CtInvocationImpl]getLocalDate()));
        }
        [CtReturnImpl]return [CtVariableReadImpl]systemNames;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem getSystemByName([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystemByName([CtVariableReadImpl]name, [CtInvocationImpl]getLocalDate());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRanks([CtParameterImpl][CtTypeReferenceImpl]Ranks r) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]ranks = [CtVariableReadImpl]r;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Ranks getRanks() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]ranks;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRankSystem([CtParameterImpl][CtTypeReferenceImpl]int system) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getRanks().setRankSystem([CtVariableReadImpl]system);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getAllRankNamesFor([CtParameterImpl][CtTypeReferenceImpl]int p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> retVal = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Rank rank : [CtInvocationImpl][CtInvocationImpl]getRanks().getAllRanks()) [CtBlockImpl]{
            [CtWhileImpl][CtCommentImpl]// Grab rank from correct profession as needed
            while ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rank.getName([CtVariableReadImpl]p).startsWith([CtLiteralImpl]"--") && [CtBinaryOperatorImpl]([CtVariableReadImpl]p != [CtFieldReadImpl]Ranks.RPROF_MW)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rank.getName([CtVariableReadImpl]p).equals([CtLiteralImpl]"--")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl][CtInvocationImpl]getRanks().getAlternateProfession([CtVariableReadImpl]p);
                } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rank.getName([CtVariableReadImpl]p).startsWith([CtLiteralImpl]"--")) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl][CtInvocationImpl]getRanks().getAlternateProfession([CtInvocationImpl][CtVariableReadImpl]rank.getName([CtVariableReadImpl]p));
                }
            } 
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]rank.getName([CtVariableReadImpl]p).equals([CtLiteralImpl]"-")) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtInvocationImpl][CtVariableReadImpl]retVal.add([CtInvocationImpl][CtVariableReadImpl]rank.getName([CtVariableReadImpl]p));
        }
        [CtReturnImpl]return [CtVariableReadImpl]retVal;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.force.Force> getAllForces() [CtBlockImpl]{
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtFieldReadImpl]forceIds.values());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setFinances([CtParameterImpl][CtTypeReferenceImpl]Finances f) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]finances = [CtVariableReadImpl]f;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Finances getFinances() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]finances;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.work.IPartWork> getPartsNeedingServiceFor([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID uid) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getPartsNeedingServiceFor([CtVariableReadImpl]uid, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.work.IPartWork> getPartsNeedingServiceFor([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID uid, [CtParameterImpl][CtTypeReferenceImpl]boolean onlyNotBeingWorkedOn) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]uid) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]uid);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]u != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.isSalvage() || [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]u.isRepairable())) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]u.getSalvageableParts([CtVariableReadImpl]onlyNotBeingWorkedOn);
            } else [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]u.getPartsNeedingFixing([CtVariableReadImpl]onlyNotBeingWorkedOn);
            }
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.ArrayList<[CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork> getAcquisitionsForUnit([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID uid) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]uid) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]uid);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]u != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]u.getPartsNeeded();
        }
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Use an A* algorithm to find the best path between two planets For right now, we are just going to minimize the number
     * of jumps but we could extend this to take advantage of recharge information or other variables as well Based on
     * http://www.policyalmanac.org/games/aStarTutorial.htm
     *
     * @param start
     * @param end
     * @return  */
    public [CtTypeReferenceImpl]mekhq.campaign.JumpPath calculateJumpPath([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem start, [CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem end) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]start) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtVariableReadImpl]end) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]start.getId().equals([CtInvocationImpl][CtVariableReadImpl]end.getId())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]JumpPath jpath = [CtConstructorCallImpl]new [CtTypeReferenceImpl]JumpPath();
            [CtInvocationImpl][CtVariableReadImpl]jpath.addSystem([CtVariableReadImpl]start);
            [CtReturnImpl]return [CtVariableReadImpl]jpath;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String startKey = [CtInvocationImpl][CtVariableReadImpl]start.getId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String endKey = [CtInvocationImpl][CtVariableReadImpl]end.getId();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String current = [CtVariableReadImpl]startKey;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> closed = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.String> open = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashSet<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean found = [CtLiteralImpl]false;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int jumps = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtCommentImpl]// we are going to through and set up some hashes that will make our
        [CtCommentImpl]// work easier
        [CtCommentImpl]// hash of parent key
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.String> parent = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtCommentImpl]// hash of H for each planet which will not change
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Double> scoreH = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtLocalVariableImpl][CtCommentImpl]// hash of G for each planet which might change
        [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Double> scoreG = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String key : [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystems().keySet()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]scoreH.put([CtVariableReadImpl]key, [CtInvocationImpl][CtVariableReadImpl]end.getDistanceTo([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystems().get([CtVariableReadImpl]key)));
        }
        [CtInvocationImpl][CtVariableReadImpl]scoreG.put([CtVariableReadImpl]current, [CtLiteralImpl]0.0);
        [CtInvocationImpl][CtVariableReadImpl]closed.add([CtVariableReadImpl]current);
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]found) && [CtBinaryOperatorImpl]([CtVariableReadImpl]jumps < [CtLiteralImpl]10000)) [CtBlockImpl]{
            [CtUnaryOperatorImpl][CtVariableWriteImpl]jumps++;
            [CtLocalVariableImpl][CtTypeReferenceImpl]double currentG = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]scoreG.get([CtVariableReadImpl]current) + [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystemById([CtVariableReadImpl]current).getRechargeTime([CtInvocationImpl]getLocalDate());
            [CtLocalVariableImpl]final [CtTypeReferenceImpl]java.lang.String localCurrent = [CtVariableReadImpl]current;
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().visitNearbySystems([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystemById([CtVariableReadImpl]current), [CtLiteralImpl]30, [CtLambdaImpl]([CtParameterImpl] p) -> [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]closed.contains([CtInvocationImpl][CtVariableReadImpl]p.getId())) [CtBlockImpl]{
                    [CtReturnImpl]return;
                } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]open.contains([CtInvocationImpl][CtVariableReadImpl]p.getId())) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// is the current G better than the existing G
                    if ([CtBinaryOperatorImpl][CtVariableReadImpl]currentG < [CtInvocationImpl][CtVariableReadImpl]scoreG.get([CtInvocationImpl][CtVariableReadImpl]p.getId())) [CtBlockImpl]{
                        [CtInvocationImpl][CtCommentImpl]// then change G and parent
                        [CtVariableReadImpl]scoreG.put([CtInvocationImpl][CtVariableReadImpl]p.getId(), [CtVariableReadImpl]currentG);
                        [CtInvocationImpl][CtVariableReadImpl]parent.put([CtInvocationImpl][CtVariableReadImpl]p.getId(), [CtVariableReadImpl]localCurrent);
                    }
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// put the current G for this one in memory
                    [CtVariableReadImpl]scoreG.put([CtInvocationImpl][CtVariableReadImpl]p.getId(), [CtVariableReadImpl]currentG);
                    [CtInvocationImpl][CtCommentImpl]// put the parent in memory
                    [CtVariableReadImpl]parent.put([CtInvocationImpl][CtVariableReadImpl]p.getId(), [CtVariableReadImpl]localCurrent);
                    [CtInvocationImpl][CtVariableReadImpl]open.add([CtInvocationImpl][CtVariableReadImpl]p.getId());
                }
            });
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String bestMatch = [CtLiteralImpl]null;
            [CtLocalVariableImpl][CtTypeReferenceImpl]double bestF = [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Double.[CtFieldReferenceImpl]POSITIVE_INFINITY;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String possible : [CtVariableReadImpl]open) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// calculate F
                [CtTypeReferenceImpl]double currentF = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]scoreG.get([CtVariableReadImpl]possible) + [CtInvocationImpl][CtVariableReadImpl]scoreH.get([CtVariableReadImpl]possible);
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]currentF < [CtVariableReadImpl]bestF) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]bestMatch = [CtVariableReadImpl]possible;
                    [CtAssignmentImpl][CtVariableWriteImpl]bestF = [CtVariableReadImpl]currentF;
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]current = [CtVariableReadImpl]bestMatch;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]current) [CtBlockImpl]{
                [CtBreakImpl][CtCommentImpl]// We're done - probably failed to find anything
                break;
            }
            [CtInvocationImpl][CtVariableReadImpl]closed.add([CtVariableReadImpl]current);
            [CtInvocationImpl][CtVariableReadImpl]open.remove([CtVariableReadImpl]current);
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]current.equals([CtVariableReadImpl]endKey)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]found = [CtLiteralImpl]true;
            }
        } 
        [CtLocalVariableImpl][CtCommentImpl]// now we just need to back up from the last current by parents until we
        [CtCommentImpl]// hit null
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem> path = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String nextKey = [CtVariableReadImpl]current;
        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]nextKey) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]path.add([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystemById([CtVariableReadImpl]nextKey));
            [CtAssignmentImpl][CtCommentImpl]// MekHQApp.logMessage(nextKey);
            [CtVariableWriteImpl]nextKey = [CtInvocationImpl][CtVariableReadImpl]parent.get([CtVariableReadImpl]nextKey);
        } 
        [CtLocalVariableImpl][CtCommentImpl]// now reverse the direction
        [CtTypeReferenceImpl]JumpPath finalPath = [CtConstructorCallImpl]new [CtTypeReferenceImpl]JumpPath();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]path.size() - [CtLiteralImpl]1; [CtBinaryOperatorImpl][CtVariableReadImpl]i >= [CtLiteralImpl]0; [CtUnaryOperatorImpl][CtVariableWriteImpl]i--) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]finalPath.addSystem([CtInvocationImpl][CtVariableReadImpl]path.get([CtVariableReadImpl]i));
        }
        [CtReturnImpl]return [CtVariableReadImpl]finalPath;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem> getAllReachableSystemsFrom([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem system) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getNearbySystems([CtVariableReadImpl]system, [CtLiteralImpl]30);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This method calculates the cost per jump for interstellar travel. It operates by fitting the part
     * of the force not transported in owned DropShips into a number of prototypical DropShips of a few
     * standard configurations, then adding the JumpShip charges on top. It remains fairly hacky, but
     * improves slightly on the prior implementation as far as following the rulebooks goes.
     *
     * It can be used to calculate total travel costs in the style of FM:Mercs (excludeOwnTransports
     * and campaignOpsCosts set to false), to calculate leased/rented travel costs only in the style
     * of FM:Mercs (excludeOwnTransports true, campaignOpsCosts false), or to calculate travel costs
     * for CampaignOps-style costs (excludeOwnTransports true, campaignOpsCosts true).
     *
     * @param excludeOwnTransports
     * 		If true, do not display maintenance costs in the calculated travel cost.
     * @param campaignOpsCosts
     * 		If true, use the Campaign Ops method for calculating travel cost. (DropShip monthly fees
     * 		of 0.5% of purchase cost, 100,000 C-bills per collar.)
     */
    [CtCommentImpl]// FIXME: Waiting for Dylan to finish re-writing
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    public [CtTypeReferenceImpl]mekhq.campaign.Money calculateCostPerJump([CtParameterImpl][CtTypeReferenceImpl]boolean excludeOwnTransports, [CtParameterImpl][CtTypeReferenceImpl]boolean campaignOpsCosts) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money collarCost = [CtInvocationImpl][CtTypeAccessImpl]Money.of([CtConditionalImpl][CtVariableReadImpl]campaignOpsCosts ? [CtLiteralImpl]100000 : [CtLiteralImpl]50000);
        [CtLocalVariableImpl][CtCommentImpl]// first we need to get the total number of units by type
        [CtTypeReferenceImpl]int nMech = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_MECH);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nLVee = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_TANK, [CtLiteralImpl]false, [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nHVee = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_TANK);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nAero = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_AERO);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nSC = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nCF = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_CONV_FIGHTER);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nBA = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_BATTLEARMOR);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nMechInf = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nMotorInf = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nFootInf = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nProto = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_PROTOMECH);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nDropship = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_DROPSHIP);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nCollars = [CtInvocationImpl]getTotalDockingCollars();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double nCargo = [CtInvocationImpl]getTotalCargoCapacity();[CtCommentImpl]// ignoring refrigerated/insulated/etc.

        [CtLocalVariableImpl][CtCommentImpl]// get cargo tonnage including parts in transit, then get mothballed unit
        [CtCommentImpl]// tonnage
        [CtTypeReferenceImpl]double carriedCargo = [CtBinaryOperatorImpl][CtInvocationImpl]getCargoTonnage([CtLiteralImpl]true, [CtLiteralImpl]false) + [CtInvocationImpl]getCargoTonnage([CtLiteralImpl]false, [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtCommentImpl]// calculate the number of units left untransported
        [CtTypeReferenceImpl]int noMech = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nMech - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_MECH), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noDS = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nDropship - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_DROPSHIP), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noSC = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nSC - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noCF = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nCF - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_CONV_FIGHTER), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noASF = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nAero - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_AERO), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nolv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nLVee - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_TANK, [CtLiteralImpl]true), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nohv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nHVee - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_TANK), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noinf = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_INFANTRY) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_INFANTRY), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noBA = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nBA - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_BATTLEARMOR), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noProto = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nProto - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_PROTOMECH), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int freehv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getTotalHeavyVehicleBays() - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_TANK), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int freeinf = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getTotalInfantryBays() - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_INFANTRY), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int freeba = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getTotalBattleArmorBays() - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_BATTLEARMOR), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int freeSC = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getTotalSmallCraftBays() - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noCargo = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]carriedCargo - [CtVariableReadImpl]nCargo, [CtLiteralImpl]0))));
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newNoASF = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]noASF - [CtVariableReadImpl]freeSC, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int placedASF = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]noASF - [CtVariableReadImpl]newNoASF, [CtLiteralImpl]0);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]freeSC -= [CtVariableReadImpl]placedASF;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newNolv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nolv - [CtVariableReadImpl]freehv, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int placedlv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nolv - [CtVariableReadImpl]newNolv, [CtLiteralImpl]0);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]freehv -= [CtVariableReadImpl]placedlv;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noVehicles = [CtBinaryOperatorImpl][CtVariableReadImpl]nohv + [CtVariableReadImpl]newNolv;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money dropshipCost;
        [CtLocalVariableImpl][CtCommentImpl]// The cost-figuring process: using prototypical dropships, figure out how
        [CtCommentImpl]// many collars are required. Charge for the prototypical dropships and
        [CtCommentImpl]// the docking collar, based on the rules selected. Allow prototypical
        [CtCommentImpl]// dropships to be leased in 1/2 increments; designs of roughly 1/2
        [CtCommentImpl]// size exist for all of the prototypical variants chosen.
        [CtCommentImpl]// DropShip costs are for the duration of the trip for FM:Mercs rules,
        [CtCommentImpl]// and per month for Campaign Ops. The prior implementation here assumed
        [CtCommentImpl]// the FM:Mercs costs were per jump, which seems reasonable. To avoid having
        [CtCommentImpl]// to add a bunch of code to remember the total length of the current
        [CtCommentImpl]// jump path, CamOps costs are normalized to per-jump, using 175 hours charge
        [CtCommentImpl]// time as a baseline.
        [CtCommentImpl]// Roughly an Overlord
        [CtTypeReferenceImpl]int largeDropshipMechCapacity = [CtLiteralImpl]36;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int largeMechDropshipASFCapacity = [CtLiteralImpl]6;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int largeMechDropshipCargoCapacity = [CtLiteralImpl]120;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money largeMechDropshipCost = [CtInvocationImpl][CtTypeAccessImpl]Money.of([CtConditionalImpl][CtVariableReadImpl]campaignOpsCosts ? [CtBinaryOperatorImpl][CtLiteralImpl]1750000.0 / [CtLiteralImpl]4.2 : [CtLiteralImpl]400000);
        [CtLocalVariableImpl][CtCommentImpl]// Roughly a Union
        [CtTypeReferenceImpl]int averageDropshipMechCapacity = [CtLiteralImpl]12;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int mechDropshipASFCapacity = [CtLiteralImpl]2;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int mechDropshipCargoCapacity = [CtLiteralImpl]75;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money mechDropshipCost = [CtInvocationImpl][CtTypeAccessImpl]Money.of([CtConditionalImpl][CtVariableReadImpl]campaignOpsCosts ? [CtBinaryOperatorImpl][CtLiteralImpl]1450000.0 / [CtLiteralImpl]4.2 : [CtLiteralImpl]150000);
        [CtLocalVariableImpl][CtCommentImpl]// Roughly a Leopard CV
        [CtTypeReferenceImpl]int averageDropshipASFCapacity = [CtLiteralImpl]6;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int asfDropshipCargoCapacity = [CtLiteralImpl]90;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money asfDropshipCost = [CtInvocationImpl][CtTypeAccessImpl]Money.of([CtConditionalImpl][CtVariableReadImpl]campaignOpsCosts ? [CtBinaryOperatorImpl][CtLiteralImpl]900000.0 / [CtLiteralImpl]4.2 : [CtLiteralImpl]80000);
        [CtLocalVariableImpl][CtCommentImpl]// Roughly a Triumph
        [CtTypeReferenceImpl]int largeDropshipVehicleCapacity = [CtLiteralImpl]50;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int largeVehicleDropshipCargoCapacity = [CtLiteralImpl]750;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money largeVehicleDropshipCost = [CtInvocationImpl][CtTypeAccessImpl]Money.of([CtConditionalImpl][CtVariableReadImpl]campaignOpsCosts ? [CtBinaryOperatorImpl][CtLiteralImpl]1750000.0 / [CtLiteralImpl]4.2 : [CtLiteralImpl]430000);
        [CtLocalVariableImpl][CtCommentImpl]// Roughly a Gazelle
        [CtTypeReferenceImpl]int averageDropshipVehicleCapacity = [CtLiteralImpl]15;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int vehicleDropshipCargoCapacity = [CtLiteralImpl]65;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money vehicleDropshipCost = [CtInvocationImpl][CtTypeAccessImpl]Money.of([CtConditionalImpl][CtVariableReadImpl]campaignOpsCosts ? [CtBinaryOperatorImpl][CtLiteralImpl]900000.0 / [CtLiteralImpl]4.2 : [CtLiteralImpl]40000);
        [CtLocalVariableImpl][CtCommentImpl]// Roughly a Mule
        [CtTypeReferenceImpl]int largeDropshipCargoCapacity = [CtLiteralImpl]8000;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money largeCargoDropshipCost = [CtInvocationImpl][CtTypeAccessImpl]Money.of([CtConditionalImpl][CtVariableReadImpl]campaignOpsCosts ? [CtBinaryOperatorImpl][CtLiteralImpl]750000.0 / [CtLiteralImpl]4.2 : [CtLiteralImpl]800000);
        [CtLocalVariableImpl][CtCommentImpl]// Roughly a Buccaneer
        [CtTypeReferenceImpl]int averageDropshipCargoCapacity = [CtLiteralImpl]2300;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money cargoDropshipCost = [CtInvocationImpl][CtTypeAccessImpl]Money.of([CtConditionalImpl][CtVariableReadImpl]campaignOpsCosts ? [CtBinaryOperatorImpl][CtLiteralImpl]550000.0 / [CtLiteralImpl]4.2 : [CtLiteralImpl]250000);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int mechCollars = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double leasedLargeMechDropships = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double leasedAverageMechDropships = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int asfCollars = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double leasedAverageASFDropships = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int vehicleCollars = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double leasedLargeVehicleDropships = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double leasedAverageVehicleDropships = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int cargoCollars = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double leasedLargeCargoDropships = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double leasedAverageCargoDropships = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int leasedASFCapacity = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int leasedCargoCapacity = [CtLiteralImpl]0;
        [CtIfImpl][CtCommentImpl]// For each type we're concerned with, calculate the number of dropships needed
        [CtCommentImpl]// to transport the force. Smaller dropships are represented by half-dropships.
        [CtCommentImpl]// If we're transporting more than a company, Overlord analogues are more efficient.
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]noMech > [CtLiteralImpl]12) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]leasedLargeMechDropships = [CtBinaryOperatorImpl][CtVariableReadImpl]noMech / [CtVariableReadImpl](([CtTypeReferenceImpl]double) (largeDropshipMechCapacity));
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]noMech -= [CtBinaryOperatorImpl][CtVariableReadImpl]leasedLargeMechDropships * [CtVariableReadImpl]largeDropshipMechCapacity;
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]mechCollars += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtVariableReadImpl]leasedLargeMechDropships)));
            [CtIfImpl][CtCommentImpl]// If there's more than a company left over, lease another Overlord. Otherwise
            [CtCommentImpl]// fall through and get a Union.
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]noMech > [CtLiteralImpl]12) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedLargeMechDropships += [CtLiteralImpl]1;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]noMech -= [CtVariableReadImpl]largeDropshipMechCapacity;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]mechCollars += [CtLiteralImpl]1;
            }
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedASFCapacity += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.floor([CtBinaryOperatorImpl][CtVariableReadImpl]leasedLargeMechDropships * [CtVariableReadImpl]largeMechDropshipASFCapacity)));
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedCargoCapacity += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.floor([CtVariableReadImpl]largeMechDropshipCargoCapacity)));
        }
        [CtIfImpl][CtCommentImpl]// Unions
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]noMech > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]leasedAverageMechDropships = [CtBinaryOperatorImpl][CtVariableReadImpl]noMech / [CtVariableReadImpl](([CtTypeReferenceImpl]double) (averageDropshipMechCapacity));
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]noMech -= [CtBinaryOperatorImpl][CtVariableReadImpl]leasedAverageMechDropships * [CtVariableReadImpl]averageDropshipMechCapacity;
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]mechCollars += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtVariableReadImpl]leasedAverageMechDropships)));
            [CtIfImpl][CtCommentImpl]// If we can fit in a smaller DropShip, lease one of those instead.
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noMech > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]noMech < [CtBinaryOperatorImpl]([CtVariableReadImpl]averageDropshipMechCapacity / [CtLiteralImpl]2))) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedAverageMechDropships += [CtLiteralImpl]0.5;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]mechCollars += [CtLiteralImpl]1;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]noMech > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedAverageMechDropships += [CtLiteralImpl]1;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]mechCollars += [CtLiteralImpl]1;
            }
            [CtOperatorAssignmentImpl][CtCommentImpl]// Our Union-ish DropShip can carry some ASFs and cargo.
            [CtVariableWriteImpl]leasedASFCapacity += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.floor([CtBinaryOperatorImpl][CtVariableReadImpl]leasedAverageMechDropships * [CtVariableReadImpl]mechDropshipASFCapacity)));
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedCargoCapacity += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.floor([CtBinaryOperatorImpl][CtVariableReadImpl]leasedAverageMechDropships * [CtVariableReadImpl]mechDropshipCargoCapacity)));
        }
        [CtIfImpl][CtCommentImpl]// Leopard CVs
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]noASF > [CtVariableReadImpl]leasedASFCapacity) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]noASF -= [CtVariableReadImpl]leasedASFCapacity;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]noASF > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]leasedAverageASFDropships = [CtBinaryOperatorImpl][CtVariableReadImpl]noASF / [CtVariableReadImpl](([CtTypeReferenceImpl]double) (averageDropshipASFCapacity));
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]noASF -= [CtBinaryOperatorImpl][CtVariableReadImpl]leasedAverageASFDropships * [CtVariableReadImpl]averageDropshipASFCapacity;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]asfCollars += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtVariableReadImpl]leasedAverageASFDropships)));
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noASF > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]noASF < [CtBinaryOperatorImpl]([CtVariableReadImpl]averageDropshipASFCapacity / [CtLiteralImpl]2))) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedAverageASFDropships += [CtLiteralImpl]0.5;
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]asfCollars += [CtLiteralImpl]1;
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]noASF > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedAverageASFDropships += [CtLiteralImpl]1;
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]asfCollars += [CtLiteralImpl]1;
                }
            }
            [CtOperatorAssignmentImpl][CtCommentImpl]// Our Leopard-ish DropShip can carry some cargo.
            [CtVariableWriteImpl]leasedCargoCapacity += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.floor([CtBinaryOperatorImpl][CtVariableReadImpl]asfDropshipCargoCapacity * [CtVariableReadImpl]leasedAverageASFDropships)));
        }
        [CtIfImpl][CtCommentImpl]// Triumphs
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]noVehicles > [CtVariableReadImpl]averageDropshipVehicleCapacity) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]leasedLargeVehicleDropships = [CtBinaryOperatorImpl][CtVariableReadImpl]noVehicles / [CtVariableReadImpl](([CtTypeReferenceImpl]double) (largeDropshipVehicleCapacity));
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]noVehicles -= [CtBinaryOperatorImpl][CtVariableReadImpl]leasedLargeVehicleDropships * [CtVariableReadImpl]largeDropshipVehicleCapacity;
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]vehicleCollars += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtVariableReadImpl]leasedLargeVehicleDropships)));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]noVehicles > [CtVariableReadImpl]averageDropshipVehicleCapacity) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedLargeVehicleDropships += [CtLiteralImpl]1;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]noVehicles -= [CtVariableReadImpl]largeDropshipVehicleCapacity;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]vehicleCollars += [CtLiteralImpl]1;
            }
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedCargoCapacity += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.floor([CtBinaryOperatorImpl][CtVariableReadImpl]leasedLargeVehicleDropships * [CtVariableReadImpl]largeVehicleDropshipCargoCapacity)));
        }
        [CtIfImpl][CtCommentImpl]// Gazelles
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]noVehicles > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]leasedAverageVehicleDropships = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]nohv + [CtVariableReadImpl]newNolv) / [CtVariableReadImpl](([CtTypeReferenceImpl]double) (averageDropshipVehicleCapacity));
            [CtAssignmentImpl][CtVariableWriteImpl]noVehicles = [CtBinaryOperatorImpl](([CtTypeReferenceImpl]int) ([CtBinaryOperatorImpl]([CtVariableReadImpl]nohv + [CtVariableReadImpl]newNolv) - [CtBinaryOperatorImpl]([CtVariableReadImpl]leasedAverageVehicleDropships * [CtVariableReadImpl]averageDropshipVehicleCapacity)));
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]vehicleCollars += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtVariableReadImpl]leasedAverageVehicleDropships)));
            [CtIfImpl][CtCommentImpl]// Gazelles are pretty minimal, so no half-measures.
            if ([CtBinaryOperatorImpl][CtVariableReadImpl]noVehicles > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedAverageVehicleDropships += [CtLiteralImpl]1;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]noVehicles -= [CtVariableReadImpl]averageDropshipVehicleCapacity;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]vehicleCollars += [CtLiteralImpl]1;
            }
            [CtOperatorAssignmentImpl][CtCommentImpl]// Our Gazelle-ish DropShip can carry some cargo.
            [CtVariableWriteImpl]leasedCargoCapacity += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.floor([CtBinaryOperatorImpl][CtVariableReadImpl]vehicleDropshipCargoCapacity * [CtVariableReadImpl]leasedAverageVehicleDropships)));
        }
        [CtOperatorAssignmentImpl][CtCommentImpl]// Do we have any leftover cargo?
        [CtVariableWriteImpl]noCargo -= [CtVariableReadImpl]leasedCargoCapacity;
        [CtIfImpl][CtCommentImpl]// Mules
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]noCargo > [CtVariableReadImpl]averageDropshipCargoCapacity) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]leasedLargeCargoDropships = [CtBinaryOperatorImpl][CtVariableReadImpl]noCargo / [CtVariableReadImpl](([CtTypeReferenceImpl]double) (largeDropshipCargoCapacity));
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]noCargo -= [CtBinaryOperatorImpl][CtVariableReadImpl]leasedLargeCargoDropships * [CtVariableReadImpl]largeDropshipCargoCapacity;
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]cargoCollars += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtVariableReadImpl]leasedLargeCargoDropships)));
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]noCargo > [CtVariableReadImpl]averageDropshipCargoCapacity) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedLargeCargoDropships += [CtLiteralImpl]1;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]noCargo -= [CtVariableReadImpl]largeDropshipCargoCapacity;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]cargoCollars += [CtLiteralImpl]1;
            }
        }
        [CtIfImpl][CtCommentImpl]// Buccaneers
        if ([CtBinaryOperatorImpl][CtVariableReadImpl]noCargo > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]leasedAverageCargoDropships = [CtBinaryOperatorImpl][CtVariableReadImpl]noCargo / [CtVariableReadImpl](([CtTypeReferenceImpl]double) (averageDropshipCargoCapacity));
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]cargoCollars += [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtVariableReadImpl]leasedAverageCargoDropships)));
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]noCargo -= [CtBinaryOperatorImpl][CtVariableReadImpl]leasedAverageCargoDropships * [CtVariableReadImpl]averageDropshipCargoCapacity;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noCargo > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]noCargo < [CtBinaryOperatorImpl]([CtVariableReadImpl]averageDropshipCargoCapacity / [CtLiteralImpl]2))) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedAverageCargoDropships += [CtLiteralImpl]0.5;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]cargoCollars += [CtLiteralImpl]1;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]noCargo > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]leasedAverageCargoDropships += [CtLiteralImpl]1;
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]cargoCollars += [CtLiteralImpl]1;
            }
        }
        [CtAssignmentImpl][CtVariableWriteImpl]dropshipCost = [CtInvocationImpl][CtVariableReadImpl]mechDropshipCost.multipliedBy([CtVariableReadImpl]leasedAverageMechDropships);
        [CtAssignmentImpl][CtVariableWriteImpl]dropshipCost = [CtInvocationImpl][CtVariableReadImpl]dropshipCost.plus([CtInvocationImpl][CtVariableReadImpl]largeMechDropshipCost.multipliedBy([CtVariableReadImpl]leasedLargeMechDropships));
        [CtAssignmentImpl][CtVariableWriteImpl]dropshipCost = [CtInvocationImpl][CtVariableReadImpl]dropshipCost.plus([CtInvocationImpl][CtVariableReadImpl]asfDropshipCost.multipliedBy([CtVariableReadImpl]leasedAverageASFDropships));
        [CtAssignmentImpl][CtVariableWriteImpl]dropshipCost = [CtInvocationImpl][CtVariableReadImpl]dropshipCost.plus([CtInvocationImpl][CtVariableReadImpl]vehicleDropshipCost.multipliedBy([CtVariableReadImpl]leasedAverageVehicleDropships));
        [CtAssignmentImpl][CtVariableWriteImpl]dropshipCost = [CtInvocationImpl][CtVariableReadImpl]dropshipCost.plus([CtInvocationImpl][CtVariableReadImpl]largeVehicleDropshipCost.multipliedBy([CtVariableReadImpl]leasedLargeVehicleDropships));
        [CtAssignmentImpl][CtVariableWriteImpl]dropshipCost = [CtInvocationImpl][CtVariableReadImpl]dropshipCost.plus([CtInvocationImpl][CtVariableReadImpl]cargoDropshipCost.multipliedBy([CtVariableReadImpl]leasedAverageCargoDropships));
        [CtAssignmentImpl][CtVariableWriteImpl]dropshipCost = [CtInvocationImpl][CtVariableReadImpl]dropshipCost.plus([CtInvocationImpl][CtVariableReadImpl]largeCargoDropshipCost.multipliedBy([CtVariableReadImpl]leasedLargeCargoDropships));
        [CtLocalVariableImpl][CtCommentImpl]// Smaller/half-DropShips are cheaper to rent, but still take one collar each
        [CtTypeReferenceImpl]int collarsNeeded = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]mechCollars + [CtVariableReadImpl]asfCollars) + [CtVariableReadImpl]vehicleCollars) + [CtVariableReadImpl]cargoCollars;
        [CtOperatorAssignmentImpl][CtCommentImpl]// add owned DropShips
        [CtVariableWriteImpl]collarsNeeded += [CtVariableReadImpl]nDropship;
        [CtAssignmentImpl][CtCommentImpl]// now factor in owned JumpShips
        [CtVariableWriteImpl]collarsNeeded = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtVariableReadImpl]collarsNeeded - [CtVariableReadImpl]nCollars);
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money totalCost = [CtInvocationImpl][CtVariableReadImpl]dropshipCost.plus([CtInvocationImpl][CtVariableReadImpl]collarCost.multipliedBy([CtVariableReadImpl]collarsNeeded));
        [CtIfImpl][CtCommentImpl]// FM:Mercs reimburses for owned transport (CamOps handles it in peacetime costs)
        if ([CtUnaryOperatorImpl]![CtVariableReadImpl]excludeOwnTransports) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Money ownDropshipCost = [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
            [CtLocalVariableImpl][CtTypeReferenceImpl]Money ownJumpshipCost = [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isMothballed()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Entity e = [CtInvocationImpl][CtVariableReadImpl]u.getEntity();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]e.getEntityType() & [CtFieldReadImpl]Entity.ETYPE_DROPSHIP) != [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]ownDropshipCost = [CtInvocationImpl][CtVariableReadImpl]ownDropshipCost.plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]mechDropshipCost.multipliedBy([CtInvocationImpl][CtVariableReadImpl]u.getMechCapacity()).dividedBy([CtVariableReadImpl]averageDropshipMechCapacity));
                        [CtAssignmentImpl][CtVariableWriteImpl]ownDropshipCost = [CtInvocationImpl][CtVariableReadImpl]ownDropshipCost.plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]asfDropshipCost.multipliedBy([CtInvocationImpl][CtVariableReadImpl]u.getASFCapacity()).dividedBy([CtVariableReadImpl]averageDropshipASFCapacity));
                        [CtAssignmentImpl][CtVariableWriteImpl]ownDropshipCost = [CtInvocationImpl][CtVariableReadImpl]ownDropshipCost.plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]vehicleDropshipCost.multipliedBy([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getHeavyVehicleCapacity() + [CtInvocationImpl][CtVariableReadImpl]u.getLightVehicleCapacity()).dividedBy([CtVariableReadImpl]averageDropshipVehicleCapacity));
                        [CtAssignmentImpl][CtVariableWriteImpl]ownDropshipCost = [CtInvocationImpl][CtVariableReadImpl]ownDropshipCost.plus([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]cargoDropshipCost.multipliedBy([CtInvocationImpl][CtVariableReadImpl]u.getCargoCapacity()).dividedBy([CtVariableReadImpl]averageDropshipCargoCapacity));
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]e.getEntityType() & [CtFieldReadImpl]Entity.ETYPE_JUMPSHIP) != [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]ownJumpshipCost = [CtInvocationImpl][CtVariableReadImpl]ownDropshipCost.plus([CtInvocationImpl][CtVariableReadImpl]collarCost.multipliedBy([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDockingCollars().size()));
                    }
                }
            }
            [CtAssignmentImpl][CtVariableWriteImpl]totalCost = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]totalCost.plus([CtVariableReadImpl]ownDropshipCost).plus([CtVariableReadImpl]ownJumpshipCost);
        }
        [CtReturnImpl]return [CtVariableReadImpl]totalCost;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void personUpdated([CtParameterImpl][CtTypeReferenceImpl]Person p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtInvocationImpl][CtVariableReadImpl]p.getUnitId());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]u) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]u.resetPilotAndEntity();
        }
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PersonChangedEvent([CtVariableReadImpl]p));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.TargetRoll getTargetFor([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IPartWork partWork, [CtParameterImpl][CtTypeReferenceImpl]Person tech) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Skill skill = [CtInvocationImpl][CtVariableReadImpl]tech.getSkillForWorkingOn([CtVariableReadImpl]partWork);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int modePenalty = [CtFieldReadImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getMode().expReduction;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]partWork.getUnit()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().isAvailable([CtBinaryOperatorImpl][CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Refit))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"This unit is not currently available!");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]partWork.getTeamId() != [CtLiteralImpl]null) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getTeamId().equals([CtInvocationImpl][CtVariableReadImpl]tech.getId()))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"Already being worked on by another team");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]skill) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"Assigned tech does not have the right skills");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().isDestroyByMargin()) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]partWork.getSkillMin() > [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]skill.getExperienceLevel() - [CtVariableReadImpl]modePenalty))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"Task is beyond this tech's skill level");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getSkillMin() > [CtFieldReadImpl]SkillType.EXP_ELITE) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"Task is impossible.");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]partWork.needsFixing()) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]partWork.isSalvaging())) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"Task is not needed.");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart) && [CtBinaryOperatorImpl]([CtLiteralImpl]null == [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.MissingPart) (partWork)).findReplacement([CtLiteralImpl]false))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"Part not available.");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Refit)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft() <= [CtLiteralImpl]0)) && [CtBinaryOperatorImpl]([CtUnaryOperatorImpl](![CtInvocationImpl]isOvertimeAllowed()) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getOvertimeLeft() <= [CtLiteralImpl]0))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"No time left.");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String notFixable = [CtInvocationImpl][CtVariableReadImpl]partWork.checkFixable();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]notFixable) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtVariableReadImpl]notFixable);
        }
        [CtIfImpl][CtCommentImpl]// if this is an infantry refit, then automatic success
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]partWork instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Refit) && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]partWork.getUnit())) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Infantry)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BattleArmor))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS, [CtLiteralImpl]"infantry refit");
        }
        [CtIfImpl][CtCommentImpl]// if we are using the MoF rule, then we will ignore mode penalty here
        [CtCommentImpl]// and instead assign it as a straight penalty
        if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().isDestroyByMargin()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]modePenalty = [CtLiteralImpl]0;
        }
        [CtLocalVariableImpl][CtCommentImpl]// this is ugly, if the mode penalty drops you to green, you drop two
        [CtCommentImpl]// levels instead of two
        [CtTypeReferenceImpl]int value = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]skill.getFinalSkillValue() + [CtVariableReadImpl]modePenalty;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]modePenalty > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtFieldReadImpl]SkillType.EXP_GREEN == [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]skill.getExperienceLevel() - [CtVariableReadImpl]modePenalty))) [CtBlockImpl]{
            [CtUnaryOperatorImpl][CtVariableWriteImpl]value++;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtVariableReadImpl]value, [CtInvocationImpl][CtTypeAccessImpl]SkillType.getExperienceLevelName([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]skill.getExperienceLevel() - [CtVariableReadImpl]modePenalty));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getValue() == [CtFieldReadImpl]TargetRoll.IMPOSSIBLE) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]target;
        }
        [CtInvocationImpl][CtVariableReadImpl]target.append([CtInvocationImpl][CtVariableReadImpl]partWork.getAllMods([CtVariableReadImpl]tech));
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useEraMods()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtInvocationImpl][CtInvocationImpl]getFaction().getEraMod([CtInvocationImpl]getGameYear()), [CtLiteralImpl]"era");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean isOvertime = [CtLiteralImpl]false;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isOvertimeAllowed() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.isTaskOvertime([CtVariableReadImpl]partWork) || [CtInvocationImpl][CtVariableReadImpl]partWork.hasWorkedOvertime())) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtLiteralImpl]3, [CtLiteralImpl]"overtime");
            [CtAssignmentImpl][CtVariableWriteImpl]isOvertime = [CtLiteralImpl]true;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int minutes = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl][CtVariableReadImpl]partWork.getTimeLeft(), [CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft());
        [CtIfImpl]if ([CtInvocationImpl]isOvertimeAllowed()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]minutes = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]minutes, [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft() + [CtInvocationImpl][CtVariableReadImpl]tech.getOvertimeLeft());
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int helpMod;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]partWork.getUnit()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().isSelfCrewed()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int hits;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getEntity().getCrew()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]hits = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getEntity().getCrew().getHits();
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]hits = [CtLiteralImpl]6;
            }
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtInvocationImpl]getShorthandedModForCrews([CtVariableReadImpl]hits);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int helpers = [CtInvocationImpl]getAvailableAstechs([CtVariableReadImpl]minutes, [CtVariableReadImpl]isOvertime);
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtInvocationImpl]getShorthandedMod([CtVariableReadImpl]helpers, [CtLiteralImpl]false);
            [CtIfImpl][CtCommentImpl]// we may have just gone overtime with our helpers
            if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]isOvertime) && [CtBinaryOperatorImpl]([CtFieldReadImpl]astechPoolMinutes < [CtBinaryOperatorImpl]([CtVariableReadImpl]minutes * [CtVariableReadImpl]helpers))) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtLiteralImpl]3, [CtLiteralImpl]"overtime astechs");
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getShorthandedMod() > [CtVariableReadImpl]helpMod) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtInvocationImpl][CtVariableReadImpl]partWork.getShorthandedMod();
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]helpMod > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtVariableReadImpl]helpMod, [CtLiteralImpl]"shorthanded");
        }
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.TargetRoll getTargetForMaintenance([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IPartWork partWork, [CtParameterImpl][CtTypeReferenceImpl]Person tech) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int value = [CtLiteralImpl]10;
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String skillLevel = [CtLiteralImpl]"Unmaintained";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]tech) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Skill skill = [CtInvocationImpl][CtVariableReadImpl]tech.getSkillForWorkingOn([CtVariableReadImpl]partWork);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]skill) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]skill.getFinalSkillValue();
                [CtAssignmentImpl][CtVariableWriteImpl]skillLevel = [CtInvocationImpl][CtTypeAccessImpl]SkillType.getExperienceLevelName([CtInvocationImpl][CtVariableReadImpl]skill.getExperienceLevel());
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtVariableReadImpl]value, [CtVariableReadImpl]skillLevel);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]target.getValue() == [CtFieldReadImpl]TargetRoll.IMPOSSIBLE) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]target;
        }
        [CtInvocationImpl][CtVariableReadImpl]target.append([CtInvocationImpl][CtVariableReadImpl]partWork.getAllModsForMaintenance());
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useEraMods()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtInvocationImpl][CtInvocationImpl]getFaction().getEraMod([CtInvocationImpl]getGameYear()), [CtLiteralImpl]"era");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]partWork.getUnit()) && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]tech)) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// we have no official rules for what happens when a tech is only
            [CtCommentImpl]// assigned
            [CtCommentImpl]// for part of the maintenance cycle, so we will create our own
            [CtCommentImpl]// penalties
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getMaintainedPct() < [CtLiteralImpl]0.5) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtLiteralImpl]2, [CtLiteralImpl]"partial maintenance");
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getMaintainedPct() < [CtLiteralImpl]1) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtLiteralImpl]1, [CtLiteralImpl]"partial maintenance");
            }
            [CtLocalVariableImpl][CtCommentImpl]// the astech issue is crazy, because you can actually be better off
            [CtCommentImpl]// not maintaining
            [CtCommentImpl]// than going it short-handed, but that is just the way it is.
            [CtCommentImpl]// Still, there is also some fuzziness about what happens if you are
            [CtCommentImpl]// short astechs
            [CtCommentImpl]// for part of the cycle. We will keep keep track of the total
            [CtCommentImpl]// "astech days" used over
            [CtCommentImpl]// the cycle and take the average per day rounding down as our team
            [CtCommentImpl]// size
            [CtTypeReferenceImpl]int helpMod = [CtLiteralImpl]0;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]partWork.getUnit()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().isSelfCrewed()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int hits = [CtLiteralImpl]0;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getEntity().getCrew()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]hits = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getEntity().getCrew().getHits();
                } else [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]hits = [CtLiteralImpl]6;
                }
                [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtInvocationImpl]getShorthandedModForCrews([CtVariableReadImpl]hits);
            } else [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int helpers = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partWork.getUnit().getAstechsMaintained();
                [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtInvocationImpl]getShorthandedMod([CtVariableReadImpl]helpers, [CtLiteralImpl]false);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]helpMod > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]target.addModifier([CtVariableReadImpl]helpMod, [CtLiteralImpl]"shorthanded");
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.TargetRoll getTargetForAcquisition([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork acquisition, [CtParameterImpl][CtTypeReferenceImpl]Person person) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getTargetForAcquisition([CtVariableReadImpl]acquisition, [CtVariableReadImpl]person, [CtLiteralImpl]true);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.TargetRoll getTargetForAcquisition([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork acquisition, [CtParameterImpl][CtTypeReferenceImpl]Person person, [CtParameterImpl][CtTypeReferenceImpl]boolean checkDaysToWait) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquisitionSkill().equals([CtTypeAccessImpl]CampaignOptions.S_AUTO)) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.AUTOMATIC_SUCCESS, [CtLiteralImpl]"Automatic Success");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]person) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"No one on your force is capable of acquiring parts");
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]Skill skill = [CtInvocationImpl][CtVariableReadImpl]person.getSkillForWorkingOn([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquisitionSkill());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtInvocationImpl]getShoppingList().getShoppingItem([CtInvocationImpl][CtVariableReadImpl]acquisition.getNewEquipment())) && [CtVariableReadImpl]checkDaysToWait) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.AUTOMATIC_FAIL, [CtLiteralImpl]"You must wait until the new cycle to check for this part. Further attempts will be added to the shopping list.");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]acquisition.getTechBase() == [CtFieldReadImpl]mekhq.campaign.parts.Part.T_CLAN) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().allowClanPurchases())) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"You cannot acquire clan parts");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]acquisition.getTechBase() == [CtFieldReadImpl]mekhq.campaign.parts.Part.T_IS) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().allowISPurchases())) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"You cannot acquire inner sphere parts");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getTechLevel() < [CtInvocationImpl][CtTypeAccessImpl]Utilities.getSimpleTechLevel([CtInvocationImpl][CtVariableReadImpl]acquisition.getTechLevel())) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"You cannot acquire parts of this tech level");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().limitByYear() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]acquisition.isIntroducedBy([CtInvocationImpl]getGameYear(), [CtInvocationImpl]useClanTechBase(), [CtInvocationImpl]getTechFaction()))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"It has not been invented yet!");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().disallowExtinctStuff() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]acquisition.isExtinctIn([CtInvocationImpl]getGameYear(), [CtInvocationImpl]useClanTechBase(), [CtInvocationImpl]getTechFaction()) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]acquisition.getAvailability() == [CtFieldReadImpl]EquipmentType.RATING_X))) [CtBlockImpl]{
            [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtLiteralImpl]"It is extinct!");
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUseAtB() && [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getRestrictPartsByMission()) && [CtBinaryOperatorImpl]([CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int partAvailability = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Part) (acquisition)).getAvailability();
            [CtLocalVariableImpl][CtTypeReferenceImpl]EquipmentType et = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]et = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) (acquisition)).getType();
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]et = [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) (acquisition)).getType();
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder partAvailabilityLog = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtBinaryOperatorImpl][CtLiteralImpl]"Part Rating Level: " + [CtVariableReadImpl]partAvailability).append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"(" + [CtArrayReadImpl][CtFieldReadImpl]EquipmentType.ratingNames[[CtVariableReadImpl]partAvailability]) + [CtLiteralImpl]")");
            [CtIfImpl][CtCommentImpl]/* Even if we can acquire Clan parts, they have a minimum availability of F for
            non-Clan units
             */
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]acquisition.getTechBase() == [CtFieldReadImpl]mekhq.campaign.parts.Part.T_CLAN) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl]getFaction().isClan())) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]partAvailability = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtVariableReadImpl]partAvailability, [CtTypeAccessImpl]EquipmentType.RATING_F);
                [CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtLiteralImpl]";[clan part for non clan faction]");
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]et != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]/* AtB rules do not simply affect difficulty of obtaining parts, but whether
                they can be obtained at all. Changing the system to use availability codes
                can have a serious effect on game play, so we apply a few tweaks to keep some
                of the more basic items from becoming completely unobtainable, while applying
                a minimum for non-flamer energy weapons, which was the reason this rule was
                included in AtB to begin with.
                 */
                if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]et instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]megamek.common.weapons.lasers.EnergyWeapon) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]et instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]megamek.common.weapons.flamers.FlamerWeapon))) && [CtBinaryOperatorImpl]([CtVariableReadImpl]partAvailability < [CtFieldReadImpl]EquipmentType.RATING_C)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]partAvailability = [CtFieldReadImpl]EquipmentType.RATING_C;
                    [CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtLiteralImpl]";(non-flamer lasers)");
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]et instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]megamek.common.weapons.autocannons.ACWeapon) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]partAvailability -= [CtLiteralImpl]2;
                    [CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtLiteralImpl]";(autocannon): -2");
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]et instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]megamek.common.weapons.gaussrifles.GaussWeapon) || [CtBinaryOperatorImpl]([CtVariableReadImpl]et instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]megamek.common.weapons.flamers.FlamerWeapon)) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]partAvailability--;
                    [CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtLiteralImpl]";(gauss rifle or flamer): -1");
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]et instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]megamek.common.AmmoType) [CtBlockImpl]{
                    [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]megamek.common.AmmoType) (et)).getAmmoType()) {
                        [CtCaseImpl]case [CtFieldReadImpl]megamek.common.AmmoType.T_AC :
                            [CtOperatorAssignmentImpl][CtVariableWriteImpl]partAvailability -= [CtLiteralImpl]2;
                            [CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtLiteralImpl]";(autocannon ammo): -2");
                            [CtBreakImpl]break;
                        [CtCaseImpl]case [CtFieldReadImpl]megamek.common.AmmoType.T_GAUSS :
                            [CtOperatorAssignmentImpl][CtVariableWriteImpl]partAvailability -= [CtLiteralImpl]1;
                            [CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtLiteralImpl]";(gauss ammo): -1");
                            [CtBreakImpl]break;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]megamek.common.AmmoType) (et)).getMunitionType() == [CtFieldReadImpl]megamek.common.AmmoType.M_STANDARD) [CtBlockImpl]{
                        [CtUnaryOperatorImpl][CtVariableWriteImpl]partAvailability--;
                        [CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtLiteralImpl]";(standard ammo): -1");
                    }
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getGameYear() < [CtLiteralImpl]2950) || [CtBinaryOperatorImpl]([CtInvocationImpl]getGameYear() > [CtLiteralImpl]3040)) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) || [CtBinaryOperatorImpl]([CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingMekActuator)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingMekCockpit)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingMekLifeSupport)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingMekLocation)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]acquisition instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.MissingMekSensor))) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]partAvailability--;
                [CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtLiteralImpl]"(Mek part prior to 2950 or after 3040): - 1");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int AtBPartsAvailability = [CtInvocationImpl]findAtBPartsAvailabilityLevel([CtVariableReadImpl]acquisition, [CtLiteralImpl]null);
            [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.append([CtBinaryOperatorImpl][CtLiteralImpl]"; Total part availability: " + [CtVariableReadImpl]partAvailability).append([CtBinaryOperatorImpl][CtLiteralImpl]"; Current campaign availability: " + [CtVariableReadImpl]AtBPartsAvailability);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]partAvailability > [CtVariableReadImpl]AtBPartsAvailability) [CtBlockImpl]{
                [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtFieldReadImpl]TargetRoll.IMPOSSIBLE, [CtInvocationImpl][CtVariableReadImpl]partAvailabilityLog.toString());
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtConstructorCallImpl]new [CtTypeReferenceImpl]TargetRoll([CtInvocationImpl][CtVariableReadImpl]skill.getFinalSkillValue(), [CtInvocationImpl][CtTypeAccessImpl]SkillType.getExperienceLevelName([CtInvocationImpl][CtVariableReadImpl]skill.getExperienceLevel()));[CtCommentImpl]// person.getTarget(Modes.MODE_NORMAL);

        [CtInvocationImpl][CtVariableReadImpl]target.append([CtInvocationImpl][CtVariableReadImpl]acquisition.getAllAcquisitionMods());
        [CtReturnImpl]return [CtVariableReadImpl]target;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract getAttachedAtBContract([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]unit) && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtFieldReadImpl]lances.get([CtInvocationImpl][CtVariableReadImpl]unit.getForceId()))) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]lances.get([CtInvocationImpl][CtVariableReadImpl]unit.getForceId()).getContract([CtThisAccessImpl]this);
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * AtB: count all available bonus parts
     *
     * @return the total <code>int</code> number of bonus parts for all active contracts
     */
    public [CtTypeReferenceImpl]int totalBonusParts() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int retVal = [CtLiteralImpl]0;
        [CtIfImpl]if ([CtInvocationImpl]hasActiveContract()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract c : [CtInvocationImpl]getActiveContracts()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]c instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]retVal += [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (c)).getNumBonusParts();
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]retVal;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void spendBonusPart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork targetWork) [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// Can only spend from active contracts, so if there are none we can't spend a bonus part
        if ([CtUnaryOperatorImpl]![CtInvocationImpl]hasActiveContract()) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String report = [CtInvocationImpl][CtVariableReadImpl]targetWork.find([CtLiteralImpl]0);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]report.endsWith([CtLiteralImpl]"0 days.")) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// First, try to spend from the contact the Acquisition's unit is attached to
            [CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract contract = [CtInvocationImpl]getAttachedAtBContract([CtInvocationImpl][CtVariableReadImpl]targetWork.getUnit());
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]contract == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtForEachImpl][CtCommentImpl]// Then, just the first free one that is active
                for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract c : [CtInvocationImpl]getActiveContracts()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (c)).getNumBonusParts() > [CtLiteralImpl]0) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]contract = [CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (c));
                        [CtBreakImpl]break;
                    }
                }
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]contract == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().error([CtThisAccessImpl]this, [CtLiteralImpl]"AtB: used bonus part but no contract has bonus parts available.");
            } else [CtBlockImpl]{
                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]resources.getString([CtLiteralImpl]"bonusPartLog.text") + [CtLiteralImpl]" ") + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]targetWork.getAcquisitionPart().getPartName());
                [CtInvocationImpl][CtVariableReadImpl]contract.useBonusPart();
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int findAtBPartsAvailabilityLevel([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork acquisition, [CtParameterImpl][CtTypeReferenceImpl]java.lang.StringBuilder reportBuilder) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract contract = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]acquisition != [CtLiteralImpl]null) ? [CtInvocationImpl]getAttachedAtBContract([CtInvocationImpl][CtVariableReadImpl]acquisition.getUnit()) : [CtLiteralImpl]null;
        [CtIfImpl][CtCommentImpl]/* If the unit is not assigned to a contract, use the least restrictive active
        contract. Don't restrict parts availability by contract if it has not started.
         */
        if ([CtInvocationImpl]hasActiveContract()) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract c : [CtInvocationImpl]getActiveContracts()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]c instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]contract == [CtLiteralImpl]null) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (c)).getPartsAvailabilityLevel() > [CtInvocationImpl][CtVariableReadImpl]contract.getPartsAvailabilityLevel()))) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]contract = [CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract) (c));
                }
            }
        }
        [CtIfImpl][CtCommentImpl]// if we have a contract and it has started
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]contract) && [CtInvocationImpl][CtInvocationImpl]getLocalDate().isBefore([CtInvocationImpl][CtVariableReadImpl]contract.getStartDate())) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]reportBuilder != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]reportBuilder.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contract.getPartsAvailabilityLevel() + [CtLiteralImpl]" (") + [CtInvocationImpl][CtVariableReadImpl]contract.getType()) + [CtLiteralImpl]")");
            }
            [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]contract.getPartsAvailabilityLevel();
        }
        [CtLocalVariableImpl][CtCommentImpl]/* If contract is still null, the unit is not in a contract. */
        [CtTypeReferenceImpl]Person adminLog = [CtInvocationImpl]findBestInRole([CtTypeAccessImpl]Person.T_ADMIN_LOG, [CtTypeAccessImpl]SkillType.S_ADMIN);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int adminLogExp = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]adminLog == [CtLiteralImpl]null) ? [CtFieldReadImpl]SkillType.EXP_ULTRA_GREEN : [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]adminLog.getSkill([CtTypeAccessImpl]SkillType.S_ADMIN).getExperienceLevel();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int adminMod = [CtBinaryOperatorImpl][CtVariableReadImpl]adminLogExp - [CtFieldReadImpl]SkillType.EXP_REGULAR;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]reportBuilder != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]reportBuilder.append([CtBinaryOperatorImpl][CtInvocationImpl]getUnitRatingMod() + [CtLiteralImpl]"(unit rating)");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]adminLog != [CtLiteralImpl]null) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]reportBuilder.append([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]adminMod + [CtLiteralImpl]"(") + [CtInvocationImpl][CtVariableReadImpl]adminLog.getFullName()) + [CtLiteralImpl]", logistics admin)");
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]reportBuilder.append([CtBinaryOperatorImpl][CtVariableReadImpl]adminMod + [CtLiteralImpl]"(no logistics admin)");
            }
        }
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]getUnitRatingMod() + [CtVariableReadImpl]adminMod;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void resetAstechMinutes() [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]480 * [CtInvocationImpl]getNumberPrimaryAstechs()) + [CtBinaryOperatorImpl]([CtLiteralImpl]240 * [CtInvocationImpl]getNumberSecondaryAstechs());
        [CtAssignmentImpl][CtFieldWriteImpl]astechPoolOvertime = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]240 * [CtInvocationImpl]getNumberPrimaryAstechs()) + [CtBinaryOperatorImpl]([CtLiteralImpl]120 * [CtInvocationImpl]getNumberSecondaryAstechs());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setAstechPoolMinutes([CtParameterImpl][CtTypeReferenceImpl]int minutes) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes = [CtVariableReadImpl]minutes;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getAstechPoolMinutes() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]astechPoolMinutes;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setAstechPoolOvertime([CtParameterImpl][CtTypeReferenceImpl]int overtime) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]astechPoolOvertime = [CtVariableReadImpl]overtime;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getAstechPoolOvertime() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]astechPoolOvertime;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getPossibleAstechPoolMinutes() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]480 * [CtInvocationImpl]getNumberPrimaryAstechs()) + [CtBinaryOperatorImpl]([CtLiteralImpl]240 * [CtInvocationImpl]getNumberSecondaryAstechs());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getPossibleAstechPoolOvertime() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]240 * [CtInvocationImpl]getNumberPrimaryAstechs()) + [CtBinaryOperatorImpl]([CtLiteralImpl]120 * [CtInvocationImpl]getNumberSecondaryAstechs());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setAstechPool([CtParameterImpl][CtTypeReferenceImpl]int size) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]astechPool = [CtVariableReadImpl]size;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getAstechPool() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]astechPool;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setMedicPool([CtParameterImpl][CtTypeReferenceImpl]int size) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]medicPool = [CtVariableReadImpl]size;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMedicPool() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]medicPool;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void increaseAstechPool([CtParameterImpl][CtTypeReferenceImpl]int i) [CtBlockImpl]{
        [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPool += [CtVariableReadImpl]i;
        [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes += [CtBinaryOperatorImpl][CtLiteralImpl]480 * [CtVariableReadImpl]i;
        [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolOvertime += [CtBinaryOperatorImpl][CtLiteralImpl]240 * [CtVariableReadImpl]i;
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.AstechPoolChangedEvent([CtThisAccessImpl]this, [CtVariableReadImpl]i));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void decreaseAstechPool([CtParameterImpl][CtTypeReferenceImpl]int i) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]astechPool = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtFieldReadImpl]astechPool - [CtVariableReadImpl]i);
        [CtAssignmentImpl][CtCommentImpl]// always assume that we fire the ones who have not yet worked
        [CtFieldWriteImpl]astechPoolMinutes = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtFieldReadImpl]astechPoolMinutes - [CtBinaryOperatorImpl]([CtLiteralImpl]480 * [CtVariableReadImpl]i));
        [CtAssignmentImpl][CtFieldWriteImpl]astechPoolOvertime = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtFieldReadImpl]astechPoolOvertime - [CtBinaryOperatorImpl]([CtLiteralImpl]240 * [CtVariableReadImpl]i));
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.AstechPoolChangedEvent([CtThisAccessImpl]this, [CtUnaryOperatorImpl]-[CtVariableReadImpl]i));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getNumberAstechs() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtInvocationImpl]getNumberPrimaryAstechs() + [CtInvocationImpl]getNumberSecondaryAstechs();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getNumberPrimaryAstechs() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int astechs = [CtInvocationImpl]getAstechPool();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_ASTECH) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.isDeployed())) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]astechs++;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]astechs;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getNumberSecondaryAstechs() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int astechs = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getSecondaryRole() == [CtFieldReadImpl]Person.T_ASTECH) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.isDeployed())) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]astechs++;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]astechs;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getAvailableAstechs([CtParameterImpl][CtTypeReferenceImpl]int minutes, [CtParameterImpl][CtTypeReferenceImpl]boolean alreadyOvertime) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int availableHelp = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.floor([CtBinaryOperatorImpl][CtFieldReadImpl](([CtTypeReferenceImpl]double) (astechPoolMinutes)) / [CtVariableReadImpl]minutes)));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl]isOvertimeAllowed() && [CtBinaryOperatorImpl]([CtVariableReadImpl]availableHelp < [CtLiteralImpl]6)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// if we are less than fully staffed, then determine whether
            [CtCommentImpl]// we should dip into overtime or just continue as short-staffed
            [CtTypeReferenceImpl]int shortMod = [CtInvocationImpl]getShorthandedMod([CtVariableReadImpl]availableHelp, [CtLiteralImpl]false);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int remainingMinutes = [CtBinaryOperatorImpl][CtFieldReadImpl]astechPoolMinutes - [CtBinaryOperatorImpl]([CtVariableReadImpl]availableHelp * [CtVariableReadImpl]minutes);
            [CtLocalVariableImpl][CtTypeReferenceImpl]int extraHelp = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]remainingMinutes + [CtFieldReadImpl]astechPoolOvertime) / [CtVariableReadImpl]minutes;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int helpNeeded = [CtBinaryOperatorImpl][CtLiteralImpl]6 - [CtVariableReadImpl]availableHelp;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]alreadyOvertime && [CtBinaryOperatorImpl]([CtVariableReadImpl]shortMod > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtCommentImpl]// then add whatever we can
                [CtVariableWriteImpl]availableHelp += [CtVariableReadImpl]extraHelp;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]shortMod > [CtLiteralImpl]3) [CtBlockImpl]{
                [CtIfImpl][CtCommentImpl]// only dip in if we can bring ourselves up to full
                if ([CtBinaryOperatorImpl][CtVariableReadImpl]extraHelp >= [CtVariableReadImpl]helpNeeded) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]availableHelp = [CtLiteralImpl]6;
                }
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]availableHelp > [CtLiteralImpl]6) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]availableHelp = [CtLiteralImpl]6;
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]availableHelp, [CtInvocationImpl]getNumberAstechs());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getShorthandedMod([CtParameterImpl][CtTypeReferenceImpl]int availableHelp, [CtParameterImpl][CtTypeReferenceImpl]boolean medicalStaff) [CtBlockImpl]{
        [CtIfImpl]if ([CtVariableReadImpl]medicalStaff) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]availableHelp += [CtLiteralImpl]2;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]int helpMod = [CtLiteralImpl]0;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]availableHelp == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtLiteralImpl]4;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]availableHelp == [CtLiteralImpl]1) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtLiteralImpl]3;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]availableHelp < [CtLiteralImpl]4) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtLiteralImpl]2;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]availableHelp < [CtLiteralImpl]6) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtLiteralImpl]1;
        }
        [CtReturnImpl]return [CtVariableReadImpl]helpMod;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getShorthandedModForCrews([CtParameterImpl][CtTypeReferenceImpl]int hits) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int helpMod = [CtLiteralImpl]0;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]hits >= [CtLiteralImpl]5) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtLiteralImpl]4;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]hits == [CtLiteralImpl]4) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtLiteralImpl]3;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]hits == [CtLiteralImpl]3) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtLiteralImpl]2;
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]hits > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]helpMod = [CtLiteralImpl]1;
        }
        [CtReturnImpl]return [CtVariableReadImpl]helpMod;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getMedicsPerDoctor() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int ndocs = [CtInvocationImpl][CtInvocationImpl]getDoctors().size();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nmedics = [CtInvocationImpl]getNumberMedics();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]ndocs == [CtLiteralImpl]0) [CtBlockImpl]{
            [CtReturnImpl]return [CtLiteralImpl]0;
        }
        [CtReturnImpl][CtCommentImpl]// TODO: figure out what to do with fractions
        return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtBinaryOperatorImpl][CtVariableReadImpl]nmedics / [CtVariableReadImpl]ndocs, [CtLiteralImpl]4);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     *
     * @return the number of medics in the campaign including any in the temporary medic pool
     */
    public [CtTypeReferenceImpl]int getNumberMedics() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int medics = [CtInvocationImpl]getMedicPool();[CtCommentImpl]// this uses a getter for unit testing

        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getActivePersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.isMedic() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.isDeployed())) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]medics++;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]medics;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void increaseMedicPool([CtParameterImpl][CtTypeReferenceImpl]int i) [CtBlockImpl]{
        [CtOperatorAssignmentImpl][CtFieldWriteImpl]medicPool += [CtVariableReadImpl]i;
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.MedicPoolChangedEvent([CtThisAccessImpl]this, [CtVariableReadImpl]i));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void decreaseMedicPool([CtParameterImpl][CtTypeReferenceImpl]int i) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]medicPool = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtLiteralImpl]0, [CtBinaryOperatorImpl][CtFieldReadImpl]medicPool - [CtVariableReadImpl]i);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.MedicPoolChangedEvent([CtThisAccessImpl]this, [CtUnaryOperatorImpl]-[CtVariableReadImpl]i));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void changeRank([CtParameterImpl][CtTypeReferenceImpl]Person person, [CtParameterImpl][CtTypeReferenceImpl]int rank, [CtParameterImpl][CtTypeReferenceImpl]boolean report) [CtBlockImpl]{
        [CtInvocationImpl]changeRank([CtVariableReadImpl]person, [CtVariableReadImpl]rank, [CtLiteralImpl]0, [CtVariableReadImpl]report);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void changeRank([CtParameterImpl][CtTypeReferenceImpl]Person person, [CtParameterImpl][CtTypeReferenceImpl]int rank, [CtParameterImpl][CtTypeReferenceImpl]int rankLevel, [CtParameterImpl][CtTypeReferenceImpl]boolean report) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int oldRank = [CtInvocationImpl][CtVariableReadImpl]person.getRankNumeric();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int oldRankLevel = [CtInvocationImpl][CtVariableReadImpl]person.getRankLevel();
        [CtInvocationImpl][CtVariableReadImpl]person.setRankNumeric([CtVariableReadImpl]rank);
        [CtInvocationImpl][CtVariableReadImpl]person.setRankLevel([CtVariableReadImpl]rankLevel);
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUseTimeInRank()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getPrisonerStatus().isFree() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]person.isDependent())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]person.setLastRankChangeDate([CtInvocationImpl]getLocalDate());
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]person.setLastRankChangeDate([CtLiteralImpl]null);
            }
        }
        [CtInvocationImpl]personUpdated([CtVariableReadImpl]person);
        [CtIfImpl]if ([CtVariableReadImpl]report) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]rank > [CtVariableReadImpl]oldRank) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]rank == [CtVariableReadImpl]oldRank) && [CtBinaryOperatorImpl]([CtVariableReadImpl]rankLevel > [CtVariableReadImpl]oldRankLevel))) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]ServiceLogger.promotedTo([CtVariableReadImpl]person, [CtInvocationImpl]getLocalDate());
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]rank < [CtVariableReadImpl]oldRank) || [CtBinaryOperatorImpl]([CtVariableReadImpl]rankLevel < [CtVariableReadImpl]oldRankLevel)) [CtBlockImpl]{
                [CtInvocationImpl][CtTypeAccessImpl]ServiceLogger.demotedTo([CtVariableReadImpl]person, [CtInvocationImpl]getLocalDate());
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]megamek.common.options.GameOptions getGameOptions() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]gameOptions;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]megamek.common.options.IBasicOption> getGameOptionsVector() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]megamek.common.options.IBasicOption> options = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Enumeration<[CtTypeReferenceImpl]megamek.common.options.IOptionGroup> i = [CtInvocationImpl][CtFieldReadImpl]gameOptions.getGroups(); [CtInvocationImpl][CtVariableReadImpl]i.hasMoreElements();) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.options.IOptionGroup group = [CtInvocationImpl][CtVariableReadImpl]i.nextElement();
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Enumeration<[CtTypeReferenceImpl]megamek.common.options.IOption> j = [CtInvocationImpl][CtVariableReadImpl]group.getOptions(); [CtInvocationImpl][CtVariableReadImpl]j.hasMoreElements();) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.options.IOption option = [CtInvocationImpl][CtVariableReadImpl]j.nextElement();
                [CtInvocationImpl][CtVariableReadImpl]options.add([CtVariableReadImpl]option);
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]options;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setGameOptions([CtParameterImpl][CtTypeReferenceImpl]megamek.common.options.GameOptions gameOptions) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl][CtThisAccessImpl]this.gameOptions = [CtVariableReadImpl]gameOptions;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setGameOptions([CtParameterImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]megamek.common.options.IBasicOption> options) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.options.IBasicOption option : [CtVariableReadImpl]options) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]gameOptions.getOption([CtInvocationImpl][CtVariableReadImpl]option.getName()).setValue([CtInvocationImpl][CtVariableReadImpl]option.getValue());
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Imports a {@link Kill} into a campaign.
     *
     * @param k
     * 		A {@link Kill} to import into the campaign.
     */
    public [CtTypeReferenceImpl]void importKill([CtParameterImpl][CtTypeReferenceImpl]Kill k) [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]kills.containsKey([CtInvocationImpl][CtVariableReadImpl]k.getPilotId())) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]kills.put([CtInvocationImpl][CtVariableReadImpl]k.getPilotId(), [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
        }
        [CtInvocationImpl][CtInvocationImpl][CtFieldReadImpl]kills.get([CtInvocationImpl][CtVariableReadImpl]k.getPilotId()).add([CtVariableReadImpl]k);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addKill([CtParameterImpl][CtTypeReferenceImpl]Kill k) [CtBlockImpl]{
        [CtInvocationImpl]importKill([CtVariableReadImpl]k);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getKillsForXP() > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getKillXPAward() > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getKillsFor([CtInvocationImpl][CtVariableReadImpl]k.getPilotId()).size() % [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getKillsForXP()) == [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Person p = [CtInvocationImpl]getPerson([CtInvocationImpl][CtVariableReadImpl]k.getPilotId());
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]p) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]p.awardXP([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getKillXPAward());
                    [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.PersonChangedEvent([CtVariableReadImpl]p));
                }
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Kill> getKills() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Kill> flattenedKills = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Kill> personKills : [CtInvocationImpl][CtFieldReadImpl]kills.values()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]flattenedKills.addAll([CtVariableReadImpl]personKills);
        }
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.unmodifiableList([CtVariableReadImpl]flattenedKills);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Kill> getKillsFor([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID pid) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Kill> personalKills = [CtInvocationImpl][CtFieldReadImpl]kills.get([CtVariableReadImpl]pid);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]personalKills == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.util.Collections.emptyList();
        }
        [CtInvocationImpl][CtVariableReadImpl]personalKills.sort([CtInvocationImpl][CtTypeAccessImpl]java.util.Comparator.comparing([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Kill::getDate));
        [CtReturnImpl]return [CtVariableReadImpl]personalKills;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.market.PartsStore getPartsStore() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]partsStore;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addCustom([CtParameterImpl][CtTypeReferenceImpl]java.lang.String name) [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]customs.add([CtVariableReadImpl]name);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean isCustom([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]customs.contains([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().getChassis() + [CtLiteralImpl]" ") + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().getModel());
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * borrowed from megamek.client
     */
    private [CtTypeReferenceImpl]void checkDuplicateNamesDuringAdd([CtParameterImpl][CtTypeReferenceImpl]Entity entity) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]duplicateNameHash.get([CtInvocationImpl][CtVariableReadImpl]entity.getShortName()) == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtInvocationImpl][CtFieldReadImpl]duplicateNameHash.put([CtInvocationImpl][CtVariableReadImpl]entity.getShortName(), [CtLiteralImpl]1);
        } else [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int count = [CtInvocationImpl][CtFieldReadImpl]duplicateNameHash.get([CtInvocationImpl][CtVariableReadImpl]entity.getShortName());
            [CtUnaryOperatorImpl][CtVariableWriteImpl]count++;
            [CtInvocationImpl][CtFieldReadImpl]duplicateNameHash.put([CtInvocationImpl][CtVariableReadImpl]entity.getShortName(), [CtVariableReadImpl]count);
            [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]entity.duplicateMarker = [CtVariableReadImpl]count;
            [CtInvocationImpl][CtVariableReadImpl]entity.generateShortName();
            [CtInvocationImpl][CtVariableReadImpl]entity.generateDisplayName();
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * If we remove a unit, we may need to update the duplicate identifier. TODO: This function is super slow :(
     *
     * @param entity
     * 		This is the entity whose name is checked for any duplicates
     */
    private [CtTypeReferenceImpl]void checkDuplicateNamesDuringDelete([CtParameterImpl][CtTypeReferenceImpl]Entity entity) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Integer o = [CtInvocationImpl][CtFieldReadImpl]duplicateNameHash.get([CtInvocationImpl][CtVariableReadImpl]entity.getShortNameRaw());
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]o != [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]int count = [CtVariableReadImpl]o;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]count > [CtLiteralImpl]1) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Entity e = [CtInvocationImpl][CtVariableReadImpl]u.getEntity();
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getShortNameRaw().equals([CtInvocationImpl][CtVariableReadImpl]entity.getShortNameRaw()) && [CtBinaryOperatorImpl]([CtFieldReadImpl][CtVariableReadImpl]e.duplicateMarker > [CtFieldReadImpl][CtVariableReadImpl]entity.duplicateMarker)) [CtBlockImpl]{
                        [CtUnaryOperatorImpl][CtFieldWriteImpl][CtVariableWriteImpl]e.duplicateMarker--;
                        [CtInvocationImpl][CtVariableReadImpl]e.generateShortName();
                        [CtInvocationImpl][CtVariableReadImpl]e.generateDisplayName();
                    }
                }
                [CtInvocationImpl][CtFieldReadImpl]duplicateNameHash.put([CtInvocationImpl][CtVariableReadImpl]entity.getShortNameRaw(), [CtBinaryOperatorImpl][CtVariableReadImpl]count - [CtLiteralImpl]1);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]duplicateNameHash.remove([CtInvocationImpl][CtVariableReadImpl]entity.getShortNameRaw());
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Hires a full complement of personnel for a given unit.
     *
     * @param uid
     * 		The unique identifier of the unit.
     */
    public [CtTypeReferenceImpl]void hirePersonnelFor([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID uid) [CtBlockImpl]{
        [CtInvocationImpl]hirePersonnelFor([CtVariableReadImpl]uid, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Hires or adds a full complement of personnel for a given unit.
     *
     * @param uid
     * 		The unique identifier of the unit.
     * @param isGM
     * 		A value indicating whether or not this action is undertaken
     * 		by a GM and should bypass any costs associated.
     */
    public [CtTypeReferenceImpl]void hirePersonnelFor([CtParameterImpl][CtTypeReferenceImpl]java.util.UUID uid, [CtParameterImpl][CtTypeReferenceImpl]boolean isGM) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]uid);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]unit) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]unit.canTakeMoreDrivers()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Person p = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]LandAirMech) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_MECHWARRIOR, [CtTypeAccessImpl]Person.T_AERO_PILOT);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Mech) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_MECHWARRIOR);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SmallCraft) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Jumpship)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_SPACE_PILOT);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ConvFighter) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_CONV_PILOT);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Aero) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_AERO_PILOT);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Tank) [CtBlockImpl]{
                [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getMovementMode()) {
                    [CtCaseImpl]case [CtFieldReadImpl]VTOL :
                        [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_VTOL_PILOT);
                        [CtBreakImpl]break;
                    [CtCaseImpl]case [CtFieldReadImpl]NAVAL :
                    [CtCaseImpl]case [CtFieldReadImpl]HYDROFOIL :
                    [CtCaseImpl]case [CtFieldReadImpl]SUBMARINE :
                        [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_NVEE_DRIVER);
                        [CtBreakImpl]break;
                    [CtCaseImpl]default :
                        [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_GVEE_DRIVER);
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Protomech) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_PROTO_PILOT);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BattleArmor) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_BA);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Infantry) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_INFANTRY);
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]p) [CtBlockImpl]{
                [CtBreakImpl]break;
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]recruitPerson([CtVariableReadImpl]p, [CtVariableReadImpl]isGM)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.usesSoloPilot() || [CtInvocationImpl][CtVariableReadImpl]unit.usesSoldiers()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]unit.addPilotOrSoldier([CtVariableReadImpl]p);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]unit.addDriver([CtVariableReadImpl]p);
            }
        } 
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]unit.canTakeMoreGunners()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Person p = [CtLiteralImpl]null;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Tank) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_VEE_GUNNER);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SmallCraft) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Jumpship)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_SPACE_GUNNER);
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Mech) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_MECHWARRIOR);
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]recruitPerson([CtVariableReadImpl]p, [CtVariableReadImpl]isGM)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtInvocationImpl][CtVariableReadImpl]unit.addGunner([CtVariableReadImpl]p);
        } 
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]unit.canTakeMoreVesselCrew()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Person p = [CtInvocationImpl]newPerson([CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().isSupportVehicle() ? [CtFieldReadImpl]Person.T_VEHICLE_CREW : [CtFieldReadImpl]Person.T_SPACE_CREW);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]recruitPerson([CtVariableReadImpl]p, [CtVariableReadImpl]isGM)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtInvocationImpl][CtVariableReadImpl]unit.addVesselCrew([CtVariableReadImpl]p);
        } 
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]unit.canTakeNavigator()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Person p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_NAVIGATOR);
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]recruitPerson([CtVariableReadImpl]p, [CtVariableReadImpl]isGM)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtInvocationImpl][CtVariableReadImpl]unit.setNavigator([CtVariableReadImpl]p);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]unit.canTakeTechOfficer()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Person p;
            [CtIfImpl][CtCommentImpl]// For vehicle command console we will default to gunner
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Tank) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_VEE_GUNNER);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]p = [CtInvocationImpl]newPerson([CtTypeAccessImpl]Person.T_MECHWARRIOR);
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl]recruitPerson([CtVariableReadImpl]p, [CtVariableReadImpl]isGM)) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtInvocationImpl][CtVariableReadImpl]unit.setTechOfficer([CtVariableReadImpl]p);
        }
        [CtInvocationImpl][CtVariableReadImpl]unit.resetPilotAndEntity();
        [CtInvocationImpl][CtVariableReadImpl]unit.runDiagnostic([CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getUnitRatingText() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getUnitRating().getUnitRating();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Against the Bot Calculates and returns dragoon rating if that is the chosen
     * method; for IOps method, returns unit reputation / 10. If the player chooses
     * not to use unit rating at all, use a default value of C. Note that the AtB
     * system is designed for use with FMMerc dragoon rating, and use of the IOps
     * Beta system may have unsatisfactory results, but we follow the options set by
     * the user here.
     */
    public [CtTypeReferenceImpl]int getUnitRatingMod() [CtBlockImpl]{
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useDragoonRating()) [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]mekhq.campaign.rating.IUnitRating.DRAGOON_C;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.rating.IUnitRating rating = [CtInvocationImpl]getUnitRating();
        [CtReturnImpl]return [CtConditionalImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUnitRatingMethod().equals([CtTypeAccessImpl]mekhq.campaign.rating.UnitRatingMethod.FLD_MAN_MERCS_REV) ? [CtInvocationImpl][CtVariableReadImpl]rating.getUnitRatingAsInteger() : [CtInvocationImpl][CtVariableReadImpl]rating.getModifier();
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This is a better method for pairing AtB with IOpts with regards to Prisoner Capture
     */
    public [CtTypeReferenceImpl]int getUnitRatingAsInteger() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useDragoonRating()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getUnitRating().getUnitRatingAsInteger();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl]mekhq.campaign.rating.IUnitRating.DRAGOON_C;
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.RandomSkillPreferences getRandomSkillPreferences() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]rskillPrefs;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRandomSkillPreferences([CtParameterImpl][CtTypeReferenceImpl]RandomSkillPreferences prefs) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]rskillPrefs = [CtVariableReadImpl]prefs;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setStartingSystem() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem> systemList = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.Systems.getInstance().getSystems();
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem startingSystem = [CtInvocationImpl][CtVariableReadImpl]systemList.get([CtInvocationImpl][CtInvocationImpl]getFaction().getStartingPlanet([CtInvocationImpl]getLocalDate()));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]startingSystem == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]startingSystem = [CtInvocationImpl][CtVariableReadImpl]systemList.get([CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showInputDialog([CtLiteralImpl]"This faction does not have a starting planet for this era. Please choose a planet."));
            [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]startingSystem == [CtLiteralImpl]null) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]startingSystem = [CtInvocationImpl][CtVariableReadImpl]systemList.get([CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showInputDialog([CtLiteralImpl]"This planet you entered does not exist. Please choose a valid planet."));
            } 
        }
        [CtAssignmentImpl][CtFieldWriteImpl]location = [CtConstructorCallImpl]new [CtTypeReferenceImpl]CurrentLocation([CtVariableReadImpl]startingSystem, [CtLiteralImpl]0);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addLogEntry([CtParameterImpl][CtTypeReferenceImpl]Person p, [CtParameterImpl][CtTypeReferenceImpl]LogEntry entry) [CtBlockImpl]{
        [CtInvocationImpl][CtVariableReadImpl]p.addLogEntry([CtVariableReadImpl]entry);
    }

    [CtMethodImpl]private [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> getPossibleRandomPortraits([CtParameterImpl][CtTypeReferenceImpl]megamek.common.util.fileUtils.DirectoryItems portraits, [CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> existingPortraits, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String subDir) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> possiblePortraits = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String> categories = [CtInvocationImpl][CtVariableReadImpl]portraits.getCategoryNames();
        [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]categories.hasNext()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String category = [CtInvocationImpl][CtVariableReadImpl]categories.next();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]category.endsWith([CtVariableReadImpl]subDir)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Iterator<[CtTypeReferenceImpl]java.lang.String> names = [CtInvocationImpl][CtVariableReadImpl]portraits.getItemNames([CtVariableReadImpl]category);
                [CtWhileImpl]while ([CtInvocationImpl][CtVariableReadImpl]names.hasNext()) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String name = [CtInvocationImpl][CtVariableReadImpl]names.next();
                    [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String location = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]category + [CtLiteralImpl]":") + [CtVariableReadImpl]name;
                    [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]existingPortraits.contains([CtVariableReadImpl]location)) [CtBlockImpl]{
                        [CtContinueImpl]continue;
                    }
                    [CtInvocationImpl][CtVariableReadImpl]possiblePortraits.add([CtVariableReadImpl]location);
                } 
            }
        } 
        [CtReturnImpl]return [CtVariableReadImpl]possiblePortraits;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void assignRandomPortraitFor([CtParameterImpl][CtTypeReferenceImpl]Person p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// first create a list of existing portrait strings, so we can check for
        [CtCommentImpl]// duplicates
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> existingPortraits = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person existingPerson : [CtInvocationImpl][CtThisAccessImpl]this.getPersonnel()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]existingPortraits.add([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]existingPerson.getPortraitCategory() + [CtLiteralImpl]":") + [CtInvocationImpl][CtVariableReadImpl]existingPerson.getPortraitFileName());
        }
        [CtLocalVariableImpl][CtCommentImpl]// TODO: it would be nice to pull the portraits directory from MekHQ
        [CtCommentImpl]// TODO: itself
        [CtTypeReferenceImpl]megamek.common.util.fileUtils.DirectoryItems portraits;
        [CtTryImpl]try [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]portraits = [CtConstructorCallImpl][CtCommentImpl]// $NON-NLS-1$ //$NON-NLS-2$
            new [CtTypeReferenceImpl]megamek.common.util.fileUtils.DirectoryItems([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.File([CtLiteralImpl]"data/images/portraits"), [CtLiteralImpl]"", [CtInvocationImpl][CtTypeAccessImpl]mekhq.gui.utilities.PortraitFileFactory.getInstance());
        }[CtCatchImpl] catch ([CtCatchVariableImpl][CtTypeReferenceImpl]java.lang.Exception e) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().error([CtInvocationImpl]getClass(), [CtLiteralImpl]"assignRandomPortraitFor", [CtVariableReadImpl]e);
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> possiblePortraits;
        [CtLocalVariableImpl][CtCommentImpl]// Will search for portraits in the /gender/primaryrole folder first,
        [CtCommentImpl]// and if none are found then /gender/rolegroup, then /gender/combat or
        [CtCommentImpl]// /gender/support, then in /gender.
        [CtTypeReferenceImpl]java.lang.String searchCat_Gender = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getGender().isFemale()) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]searchCat_Gender += [CtLiteralImpl]"Female/";
        } else [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]searchCat_Gender += [CtLiteralImpl]"Male/";
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String searchCat_Role = [CtBinaryOperatorImpl][CtInvocationImpl][CtTypeAccessImpl]Person.getRoleDesc([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole(), [CtLiteralImpl]true) + [CtLiteralImpl]"/";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String searchCat_RoleGroup = [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String searchCat_CombatSupport = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_ADMIN_COM) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_ADMIN_HR)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_ADMIN_LOG)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_ADMIN_TRA)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]searchCat_RoleGroup = [CtLiteralImpl]"Admin/";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_MECHANIC) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_AERO_TECH)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_MECH_TECH)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_BA_TECH)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]searchCat_RoleGroup = [CtLiteralImpl]"Tech/";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_MEDIC) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_DOCTOR)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]searchCat_RoleGroup = [CtLiteralImpl]"Medical/";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_SPACE_CREW) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_SPACE_GUNNER)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_SPACE_PILOT)) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_NAVIGATOR)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]searchCat_RoleGroup = [CtLiteralImpl]"Vessel Crew/";
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.hasPrimarySupportRole([CtLiteralImpl]true)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]searchCat_CombatSupport = [CtLiteralImpl]"Support/";
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]searchCat_CombatSupport = [CtLiteralImpl]"Combat/";
        }
        [CtAssignmentImpl][CtVariableWriteImpl]possiblePortraits = [CtInvocationImpl]getPossibleRandomPortraits([CtVariableReadImpl]portraits, [CtVariableReadImpl]existingPortraits, [CtBinaryOperatorImpl][CtVariableReadImpl]searchCat_Gender + [CtVariableReadImpl]searchCat_Role);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]possiblePortraits.isEmpty() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]searchCat_RoleGroup.isEmpty())) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]possiblePortraits = [CtInvocationImpl]getPossibleRandomPortraits([CtVariableReadImpl]portraits, [CtVariableReadImpl]existingPortraits, [CtBinaryOperatorImpl][CtVariableReadImpl]searchCat_Gender + [CtVariableReadImpl]searchCat_RoleGroup);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]possiblePortraits.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]possiblePortraits = [CtInvocationImpl]getPossibleRandomPortraits([CtVariableReadImpl]portraits, [CtVariableReadImpl]existingPortraits, [CtBinaryOperatorImpl][CtVariableReadImpl]searchCat_Gender + [CtVariableReadImpl]searchCat_CombatSupport);
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]possiblePortraits.isEmpty()) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]possiblePortraits = [CtInvocationImpl]getPossibleRandomPortraits([CtVariableReadImpl]portraits, [CtVariableReadImpl]existingPortraits, [CtVariableReadImpl]searchCat_Gender);
        }
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]possiblePortraits.isEmpty()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String chosenPortrait = [CtInvocationImpl][CtVariableReadImpl]possiblePortraits.get([CtInvocationImpl][CtTypeAccessImpl]Compute.randomInt([CtInvocationImpl][CtVariableReadImpl]possiblePortraits.size()));
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] temp = [CtInvocationImpl][CtVariableReadImpl]chosenPortrait.split([CtLiteralImpl]":");
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl][CtVariableReadImpl]temp.length != [CtLiteralImpl]2) [CtBlockImpl]{
                [CtReturnImpl]return;
            }
            [CtInvocationImpl][CtVariableReadImpl]p.setPortraitCategory([CtArrayReadImpl][CtVariableReadImpl]temp[[CtLiteralImpl]0]);
            [CtInvocationImpl][CtVariableReadImpl]p.setPortraitFileName([CtArrayReadImpl][CtVariableReadImpl]temp[[CtLiteralImpl]1]);
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Assigns a random origin to a {@link Person}.
     *
     * @param p
     * 		The {@link Person} who should receive a randomized origin.
     */
    public [CtTypeReferenceImpl]void assignRandomOriginFor([CtParameterImpl][CtTypeReferenceImpl]Person p) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.AbstractFactionSelector factionSelector = [CtInvocationImpl]getFactionSelector();
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.AbstractPlanetSelector planetSelector = [CtInvocationImpl]getPlanetSelector();
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.Faction faction = [CtInvocationImpl][CtVariableReadImpl]factionSelector.selectFaction([CtThisAccessImpl]this);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.universe.Planet planet = [CtInvocationImpl][CtVariableReadImpl]planetSelector.selectPlanet([CtThisAccessImpl]this, [CtVariableReadImpl]faction);
        [CtInvocationImpl][CtVariableReadImpl]p.setOriginFaction([CtVariableReadImpl]faction);
        [CtInvocationImpl][CtVariableReadImpl]p.setOriginPlanet([CtVariableReadImpl]planet);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void clearGameData([CtParameterImpl][CtTypeReferenceImpl]Entity entity) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Mounted m : [CtInvocationImpl][CtVariableReadImpl]entity.getEquipment()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]m.setUsedThisRound([CtLiteralImpl]false);
            [CtInvocationImpl][CtVariableReadImpl]m.resetJam();
        }
        [CtInvocationImpl][CtVariableReadImpl]entity.setDeployed([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]entity.setElevation([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]entity.setPassedThrough([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>());
        [CtInvocationImpl][CtVariableReadImpl]entity.resetFiringArcs();
        [CtInvocationImpl][CtVariableReadImpl]entity.resetBays();
        [CtInvocationImpl][CtVariableReadImpl]entity.setEvading([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]entity.setFacing([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]entity.setPosition([CtLiteralImpl]null);
        [CtInvocationImpl][CtVariableReadImpl]entity.setProne([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]entity.setHullDown([CtLiteralImpl]false);
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]entity.heat = [CtLiteralImpl]0;
        [CtAssignmentImpl][CtFieldWriteImpl][CtVariableWriteImpl]entity.heatBuildup = [CtLiteralImpl]0;
        [CtInvocationImpl][CtVariableReadImpl]entity.setTransportId([CtTypeAccessImpl]Entity.NONE);
        [CtInvocationImpl][CtVariableReadImpl]entity.resetTransporter();
        [CtInvocationImpl][CtVariableReadImpl]entity.setDeployRound([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]entity.setSwarmAttackerId([CtTypeAccessImpl]Entity.NONE);
        [CtInvocationImpl][CtVariableReadImpl]entity.setSwarmTargetId([CtTypeAccessImpl]Entity.NONE);
        [CtInvocationImpl][CtVariableReadImpl]entity.setUnloaded([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]entity.setDone([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]entity.setLastTarget([CtTypeAccessImpl]Entity.NONE);
        [CtInvocationImpl][CtVariableReadImpl]entity.setNeverDeployed([CtLiteralImpl]true);
        [CtInvocationImpl][CtVariableReadImpl]entity.setStuck([CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]entity.resetCoolantFailureAmount();
        [CtInvocationImpl][CtVariableReadImpl]entity.setConversionMode([CtLiteralImpl]0);
        [CtInvocationImpl][CtVariableReadImpl]entity.setDoomed([CtLiteralImpl]false);
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entity.getSensors().isEmpty()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]entity.setNextSensor([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entity.getSensors().firstElement());
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]IBomber) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]IBomber bomber = [CtVariableReadImpl](([CtTypeReferenceImpl]IBomber) (entity));
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Mounted> mountedBombs = [CtInvocationImpl][CtVariableReadImpl]bomber.getBombs();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]mountedBombs.size() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl][CtCommentImpl]// This should return an int[] filled with 0's
                [CtArrayTypeReferenceImpl]int[] bombChoices = [CtInvocationImpl][CtVariableReadImpl]bomber.getBombChoices();
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Mounted m : [CtVariableReadImpl]mountedBombs) [CtBlockImpl]{
                    [CtIfImpl]if ([CtUnaryOperatorImpl]![CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]m.getType() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BombType)) [CtBlockImpl]{
                        [CtContinueImpl]continue;
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]m.getBaseShotsLeft() == [CtLiteralImpl]1) [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]bombChoices[[CtInvocationImpl][CtTypeAccessImpl]BombType.getBombTypeFromInternalName([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]m.getType().getInternalName())] += [CtLiteralImpl]1;
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]bomber.setBombChoices([CtVariableReadImpl]bombChoices);
                [CtInvocationImpl][CtVariableReadImpl]bomber.clearBombs();
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Mech) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Mech m = [CtVariableReadImpl](([CtTypeReferenceImpl]Mech) (entity));
            [CtInvocationImpl][CtVariableReadImpl]m.setCoolingFlawActive([CtLiteralImpl]false);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Aero) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Aero a = [CtVariableReadImpl](([CtTypeReferenceImpl]Aero) (entity));
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]a.isSpheroid()) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]entity.setMovementMode([CtTypeAccessImpl]EntityMovementMode.SPHEROID);
            } else [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]entity.setMovementMode([CtTypeAccessImpl]EntityMovementMode.AERODYNE);
            }
            [CtInvocationImpl][CtVariableReadImpl]a.setAltitude([CtLiteralImpl]5);
            [CtInvocationImpl][CtVariableReadImpl]a.setCurrentVelocity([CtLiteralImpl]0);
            [CtInvocationImpl][CtVariableReadImpl]a.setNextVelocity([CtLiteralImpl]0);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]entity instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Tank) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Tank t = [CtVariableReadImpl](([CtTypeReferenceImpl]Tank) (entity));
            [CtInvocationImpl][CtVariableReadImpl]t.unjamTurret([CtInvocationImpl][CtVariableReadImpl]t.getLocTurret());
            [CtInvocationImpl][CtVariableReadImpl]t.unjamTurret([CtInvocationImpl][CtVariableReadImpl]t.getLocTurret2());
            [CtInvocationImpl][CtVariableReadImpl]t.resetJammedWeapons();
        }
        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entity.getSecondaryPositions().clear();
        [CtInvocationImpl][CtCommentImpl]// TODO: still a lot of stuff to do here, but oh well
        [CtVariableReadImpl]entity.setOwner([CtFieldReadImpl]player);
        [CtInvocationImpl][CtVariableReadImpl]entity.setGame([CtFieldReadImpl]game);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.parts.Part checkForExistingSparePart([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part) [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part spare : [CtInvocationImpl][CtFieldReadImpl]parts.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]spare.isSpare()) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]spare.getId() == [CtInvocationImpl][CtVariableReadImpl]part.getId())) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]part.isSamePartTypeAndStatus([CtVariableReadImpl]spare)) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]spare;
            }
        }
        [CtReturnImpl]return [CtLiteralImpl]null;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void refreshNetworks() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// we are going to rebuild the c3, nc3 and c3i networks based on
            [CtCommentImpl]// the c3UUIDs
            [CtCommentImpl]// TODO: can we do this more efficiently?
            [CtCommentImpl]// this code is cribbed from megamek.server#receiveEntityAdd
            [CtTypeReferenceImpl]Entity entity = [CtInvocationImpl][CtVariableReadImpl]unit.getEntity();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]entity) && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]entity.hasC3() || [CtInvocationImpl][CtVariableReadImpl]entity.hasC3i()) || [CtInvocationImpl][CtVariableReadImpl]entity.hasNavalC3())) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean C3iSet = [CtLiteralImpl]false;
                [CtLocalVariableImpl][CtTypeReferenceImpl]boolean NC3Set = [CtLiteralImpl]false;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Entity e : [CtInvocationImpl][CtFieldReadImpl]game.getEntitiesVector()) [CtBlockImpl]{
                    [CtIfImpl][CtCommentImpl]// C3 Checks
                    if ([CtInvocationImpl][CtVariableReadImpl]entity.hasC3()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]entity.getC3MasterIsUUIDAsString() != [CtLiteralImpl]null) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entity.getC3MasterIsUUIDAsString().equals([CtInvocationImpl][CtVariableReadImpl]e.getC3UUIDAsString())) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]entity.setC3Master([CtVariableReadImpl]e, [CtLiteralImpl]false);
                            [CtBreakImpl]break;
                        }
                    }
                    [CtIfImpl][CtCommentImpl]// Naval C3 checks
                    if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]entity.hasNavalC3() && [CtUnaryOperatorImpl](![CtVariableReadImpl]NC3Set)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]entity.setC3NetIdSelf();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtLiteralImpl]0;
                        [CtWhileImpl][CtCommentImpl]// Well, they're the same value of 6...
                        while ([CtBinaryOperatorImpl][CtVariableReadImpl]pos < [CtFieldReadImpl]Entity.MAX_C3i_NODES) [CtBlockImpl]{
                            [CtIfImpl][CtCommentImpl]// We've found a network, join it.
                            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]entity.getNC3NextUUIDAsString([CtVariableReadImpl]pos) != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]e.getC3UUIDAsString() != [CtLiteralImpl]null)) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entity.getNC3NextUUIDAsString([CtVariableReadImpl]pos).equals([CtInvocationImpl][CtVariableReadImpl]e.getC3UUIDAsString())) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]entity.setC3NetId([CtVariableReadImpl]e);
                                [CtAssignmentImpl][CtVariableWriteImpl]NC3Set = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                            [CtUnaryOperatorImpl][CtVariableWriteImpl]pos++;
                        } 
                    }
                    [CtIfImpl][CtCommentImpl]// C3i Checks
                    if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]entity.hasC3i() && [CtUnaryOperatorImpl](![CtVariableReadImpl]C3iSet)) [CtBlockImpl]{
                        [CtInvocationImpl][CtVariableReadImpl]entity.setC3NetIdSelf();
                        [CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtLiteralImpl]0;
                        [CtWhileImpl]while ([CtBinaryOperatorImpl][CtVariableReadImpl]pos < [CtFieldReadImpl]Entity.MAX_C3i_NODES) [CtBlockImpl]{
                            [CtIfImpl][CtCommentImpl]// We've found a network, join it.
                            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]entity.getC3iNextUUIDAsString([CtVariableReadImpl]pos) != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]e.getC3UUIDAsString() != [CtLiteralImpl]null)) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entity.getC3iNextUUIDAsString([CtVariableReadImpl]pos).equals([CtInvocationImpl][CtVariableReadImpl]e.getC3UUIDAsString())) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]entity.setC3NetId([CtVariableReadImpl]e);
                                [CtAssignmentImpl][CtVariableWriteImpl]C3iSet = [CtLiteralImpl]true;
                                [CtBreakImpl]break;
                            }
                            [CtUnaryOperatorImpl][CtVariableWriteImpl]pos++;
                        } 
                    }
                }
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void disbandNetworkOf([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// collect all of the other units on this network to rebuild the uuids
        [CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> networkedUnits = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3NetId()) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3NetId().equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().getC3NetId())) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]networkedUnits.add([CtVariableReadImpl]unit);
            }
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]pos < [CtFieldReadImpl]Entity.MAX_C3i_NODES; [CtUnaryOperatorImpl][CtVariableWriteImpl]pos++) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit nUnit : [CtVariableReadImpl]networkedUnits) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().hasNavalC3()) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setNC3NextUUIDAsString([CtVariableReadImpl]pos, [CtLiteralImpl]null);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setC3iNextUUIDAsString([CtVariableReadImpl]pos, [CtLiteralImpl]null);
                }
            }
        }
        [CtInvocationImpl]refreshNetworks();
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.NetworkChangedEvent([CtVariableReadImpl]networkedUnits));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeUnitsFromNetwork([CtParameterImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> removedUnits) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// collect all of the other units on this network to rebuild the uuids
        [CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]java.lang.String> uuids = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> networkedUnits = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String network = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]removedUnits.get([CtLiteralImpl]0).getEntity().getC3NetId();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]removedUnits.contains([CtVariableReadImpl]unit)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3NetId()) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3NetId().equals([CtVariableReadImpl]network)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]networkedUnits.add([CtVariableReadImpl]unit);
                [CtInvocationImpl][CtVariableReadImpl]uuids.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3UUIDAsString());
            }
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]pos < [CtFieldReadImpl]Entity.MAX_C3i_NODES; [CtUnaryOperatorImpl][CtVariableWriteImpl]pos++) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtVariableReadImpl]removedUnits) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasNavalC3()) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().setNC3NextUUIDAsString([CtVariableReadImpl]pos, [CtLiteralImpl]null);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().setC3iNextUUIDAsString([CtVariableReadImpl]pos, [CtLiteralImpl]null);
                }
            }
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit nUnit : [CtVariableReadImpl]networkedUnits) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pos < [CtInvocationImpl][CtVariableReadImpl]uuids.size()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().hasNavalC3()) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setNC3NextUUIDAsString([CtVariableReadImpl]pos, [CtInvocationImpl][CtVariableReadImpl]uuids.get([CtVariableReadImpl]pos));
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setC3iNextUUIDAsString([CtVariableReadImpl]pos, [CtInvocationImpl][CtVariableReadImpl]uuids.get([CtVariableReadImpl]pos));
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().hasNavalC3()) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setNC3NextUUIDAsString([CtVariableReadImpl]pos, [CtLiteralImpl]null);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setC3iNextUUIDAsString([CtVariableReadImpl]pos, [CtLiteralImpl]null);
                }
            }
        }
        [CtInvocationImpl]refreshNetworks();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addUnitsToNetwork([CtParameterImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> addedUnits, [CtParameterImpl][CtTypeReferenceImpl]java.lang.String netid) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// collect all of the other units on this network to rebuild the uuids
        [CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]java.lang.String> uuids = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> networkedUnits = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtVariableReadImpl]addedUnits) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]uuids.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().getC3UUIDAsString());
            [CtInvocationImpl][CtVariableReadImpl]networkedUnits.add([CtVariableReadImpl]u);
        }
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]addedUnits.contains([CtVariableReadImpl]unit)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3NetId()) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3NetId().equals([CtVariableReadImpl]netid)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]networkedUnits.add([CtVariableReadImpl]unit);
                [CtInvocationImpl][CtVariableReadImpl]uuids.add([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3UUIDAsString());
            }
        }
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int pos = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]pos < [CtFieldReadImpl]Entity.MAX_C3i_NODES; [CtUnaryOperatorImpl][CtVariableWriteImpl]pos++) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit nUnit : [CtVariableReadImpl]networkedUnits) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]pos < [CtInvocationImpl][CtVariableReadImpl]uuids.size()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().hasNavalC3()) [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setNC3NextUUIDAsString([CtVariableReadImpl]pos, [CtInvocationImpl][CtVariableReadImpl]uuids.get([CtVariableReadImpl]pos));
                    } else [CtBlockImpl]{
                        [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setC3iNextUUIDAsString([CtVariableReadImpl]pos, [CtInvocationImpl][CtVariableReadImpl]uuids.get([CtVariableReadImpl]pos));
                    }
                } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().hasNavalC3()) [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setNC3NextUUIDAsString([CtVariableReadImpl]pos, [CtLiteralImpl]null);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]nUnit.getEntity().setC3iNextUUIDAsString([CtVariableReadImpl]pos, [CtLiteralImpl]null);
                }
            }
        }
        [CtInvocationImpl]refreshNetworks();
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.NetworkChangedEvent([CtVariableReadImpl]addedUnits));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Vector<[CtArrayTypeReferenceImpl]java.lang.String[]> getAvailableC3iNetworks() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtArrayTypeReferenceImpl]java.lang.String[]> networks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]java.lang.String> networkNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getForceId() < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtContinueImpl][CtCommentImpl]// only units currently in the TO&E
                continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]u.getEntity();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]en) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.hasC3i() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3Nodes() < [CtLiteralImpl]5)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3Nodes() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] network = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]2];
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]0] = [CtInvocationImpl][CtVariableReadImpl]en.getC3NetId();
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]1] = [CtBinaryOperatorImpl][CtLiteralImpl]"" + [CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3Nodes();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]networkNames.contains([CtArrayReadImpl][CtVariableReadImpl]network[[CtLiteralImpl]0])) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]networks.add([CtVariableReadImpl]network);
                    [CtInvocationImpl][CtVariableReadImpl]networkNames.add([CtArrayReadImpl][CtVariableReadImpl]network[[CtLiteralImpl]0]);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]networks;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Method that returns a Vector of the unique name Strings of all Naval C3 networks that have at least 1 free node
     * Adapted from getAvailableC3iNetworks() as the two technologies have very similar workings
     *
     * @return  */
    public [CtTypeReferenceImpl]java.util.Vector<[CtArrayTypeReferenceImpl]java.lang.String[]> getAvailableNC3Networks() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtArrayTypeReferenceImpl]java.lang.String[]> networks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]java.lang.String> networkNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getForceId() < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtContinueImpl][CtCommentImpl]// only units currently in the TO&E
                continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]u.getEntity();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]en) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.hasNavalC3() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3Nodes() < [CtLiteralImpl]5)) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3Nodes() > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] network = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]2];
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]0] = [CtInvocationImpl][CtVariableReadImpl]en.getC3NetId();
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]1] = [CtBinaryOperatorImpl][CtLiteralImpl]"" + [CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3Nodes();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]networkNames.contains([CtArrayReadImpl][CtVariableReadImpl]network[[CtLiteralImpl]0])) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]networks.add([CtVariableReadImpl]network);
                    [CtInvocationImpl][CtVariableReadImpl]networkNames.add([CtArrayReadImpl][CtVariableReadImpl]network[[CtLiteralImpl]0]);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]networks;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Vector<[CtArrayTypeReferenceImpl]java.lang.String[]> getAvailableC3MastersForSlaves() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtArrayTypeReferenceImpl]java.lang.String[]> networks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]java.lang.String> networkNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getForceId() < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtContinueImpl][CtCommentImpl]// only units currently in the TO&E
                continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]u.getEntity();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]en) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl][CtCommentImpl]// count of free c3 nodes for single company-level masters
            [CtCommentImpl]// will not be right so skip
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.hasC3M() && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]en.hasC3MM())) && [CtInvocationImpl][CtVariableReadImpl]en.C3MasterIs([CtVariableReadImpl]en)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3Nodes() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] network = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]3];
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]0] = [CtInvocationImpl][CtVariableReadImpl]en.getC3UUIDAsString();
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]1] = [CtBinaryOperatorImpl][CtLiteralImpl]"" + [CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3Nodes();
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]2] = [CtBinaryOperatorImpl][CtLiteralImpl]"" + [CtInvocationImpl][CtVariableReadImpl]en.getShortName();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]networkNames.contains([CtArrayReadImpl][CtVariableReadImpl]network[[CtLiteralImpl]0])) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]networks.add([CtVariableReadImpl]network);
                    [CtInvocationImpl][CtVariableReadImpl]networkNames.add([CtArrayReadImpl][CtVariableReadImpl]network[[CtLiteralImpl]0]);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]networks;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.util.Vector<[CtArrayTypeReferenceImpl]java.lang.String[]> getAvailableC3MastersForMasters() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtArrayTypeReferenceImpl]java.lang.String[]> networks = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Vector<[CtTypeReferenceImpl]java.lang.String> networkNames = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.Vector<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getForceId() < [CtLiteralImpl]0) [CtBlockImpl]{
                [CtContinueImpl][CtCommentImpl]// only units currently in the TO&E
                continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]u.getEntity();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]en) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3MNodes() > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.String[] network = [CtNewArrayImpl]new [CtTypeReferenceImpl]java.lang.String[[CtLiteralImpl]3];
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]0] = [CtInvocationImpl][CtVariableReadImpl]en.getC3UUIDAsString();
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]1] = [CtBinaryOperatorImpl][CtLiteralImpl]"" + [CtInvocationImpl][CtVariableReadImpl]en.calculateFreeC3MNodes();
                [CtAssignmentImpl][CtArrayWriteImpl][CtVariableReadImpl]network[[CtLiteralImpl]2] = [CtBinaryOperatorImpl][CtLiteralImpl]"" + [CtInvocationImpl][CtVariableReadImpl]en.getShortName();
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]networkNames.contains([CtArrayReadImpl][CtVariableReadImpl]network[[CtLiteralImpl]0])) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]networks.add([CtVariableReadImpl]network);
                    [CtInvocationImpl][CtVariableReadImpl]networkNames.add([CtArrayReadImpl][CtVariableReadImpl]network[[CtLiteralImpl]0]);
                }
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]networks;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void removeUnitsFromC3Master([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit master) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]mekhq.campaign.unit.Unit> removed = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3MasterIsUUIDAsString()) && [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().getC3MasterIsUUIDAsString().equals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]master.getEntity().getC3UUIDAsString())) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().setC3MasterIsUUIDAsString([CtLiteralImpl]null);
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]unit.getEntity().setC3Master([CtLiteralImpl]null, [CtLiteralImpl]true);
                [CtInvocationImpl][CtVariableReadImpl]removed.add([CtVariableReadImpl]unit);
            }
        }
        [CtInvocationImpl]refreshNetworks();
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.NetworkChangedEvent([CtVariableReadImpl]removed));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This function reloads the game entities into the game at the end of scenario resolution, so that entities are
     * properly updated and destroyed ones removed
     */
    public [CtTypeReferenceImpl]void reloadGameEntities() [CtBlockImpl]{
        [CtInvocationImpl][CtFieldReadImpl]game.reset();
        [CtInvocationImpl][CtInvocationImpl]getHangar().forEachUnit([CtLambdaImpl]([CtParameterImpl] u) -> [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]u.getEntity();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]en) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl][CtFieldReferenceImpl]game.addEntity([CtInvocationImpl][CtVariableReadImpl]en.getId(), [CtVariableReadImpl]en);
            }
        });
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void completeMission([CtParameterImpl][CtTypeReferenceImpl]int id, [CtParameterImpl][CtTypeReferenceImpl]int status) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission mission = [CtInvocationImpl]getMission([CtVariableReadImpl]id);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]mission) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtInvocationImpl][CtVariableReadImpl]mission.setStatus([CtVariableReadImpl]status);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]mission instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract contract = [CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.Contract) (mission));
            [CtLocalVariableImpl][CtTypeReferenceImpl]Money remainingMoney = [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
            [CtIfImpl][CtCommentImpl]// check for money in escrow
            [CtCommentImpl]// According to FMM(r) pg 179, both failure and breach lead to no
            [CtCommentImpl]// further payment even though this seems stupid
            if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contract.getStatus() == [CtFieldReadImpl]mekhq.campaign.mission.Mission.S_SUCCESS) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contract.getMonthsLeft([CtInvocationImpl]getLocalDate()) > [CtLiteralImpl]0)) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]remainingMoney = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contract.getMonthlyPayOut().multipliedBy([CtInvocationImpl][CtVariableReadImpl]contract.getMonthsLeft([CtInvocationImpl]getLocalDate()));
            }
            [CtIfImpl][CtCommentImpl]// If overage repayment is enabled, we first need to check if the salvage percent is
            [CtCommentImpl]// under 100. 100 means you cannot have a overage.
            [CtCommentImpl]// Then, we check if the salvage percent is less than the percent salvaged by the
            [CtCommentImpl]// unit in question. If it is, then they owe the assigner some cash
            if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getOverageRepaymentInFinalPayment() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]contract.getSalvagePct() < [CtLiteralImpl]100)) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]Money totalSalvaged = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contract.getSalvagedByEmployer().plus([CtInvocationImpl][CtVariableReadImpl]contract.getSalvagedByUnit());
                [CtLocalVariableImpl][CtTypeReferenceImpl]double percentSalvaged = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]contract.getSalvagedByUnit().getAmount().doubleValue() / [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]totalSalvaged.getAmount().doubleValue();
                [CtLocalVariableImpl][CtTypeReferenceImpl]double salvagePercent = [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]contract.getSalvagePct() / [CtLiteralImpl]100.0;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]salvagePercent < [CtVariableReadImpl]percentSalvaged) [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]Money amountToRepay = [CtInvocationImpl][CtVariableReadImpl]totalSalvaged.multipliedBy([CtBinaryOperatorImpl][CtVariableReadImpl]percentSalvaged - [CtVariableReadImpl]salvagePercent);
                    [CtAssignmentImpl][CtVariableWriteImpl]remainingMoney = [CtInvocationImpl][CtVariableReadImpl]remainingMoney.minus([CtVariableReadImpl]amountToRepay);
                    [CtInvocationImpl][CtVariableReadImpl]contract.subtractSalvageByUnit([CtVariableReadImpl]amountToRepay);
                }
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]remainingMoney.isPositive()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]finances.credit([CtVariableReadImpl]remainingMoney, [CtTypeAccessImpl]Transaction.C_CONTRACT, [CtBinaryOperatorImpl][CtLiteralImpl]"Remaining payment for " + [CtInvocationImpl][CtVariableReadImpl]contract.getName(), [CtInvocationImpl]getLocalDate());
                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Your account has been credited for " + [CtInvocationImpl][CtVariableReadImpl]remainingMoney.toAmountAndSymbolString()) + [CtLiteralImpl]" for the remaining payout from contract ") + [CtInvocationImpl][CtVariableReadImpl]contract.getName());
            } else [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]remainingMoney.isNegative()) [CtBlockImpl]{
                [CtInvocationImpl][CtFieldReadImpl]finances.debit([CtVariableReadImpl]remainingMoney, [CtTypeAccessImpl]Transaction.C_CONTRACT, [CtBinaryOperatorImpl][CtLiteralImpl]"Repaying payment overages for " + [CtInvocationImpl][CtVariableReadImpl]contract.getName(), [CtInvocationImpl]getLocalDate());
                [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"Your account has been debited for " + [CtInvocationImpl][CtVariableReadImpl]remainingMoney.toAmountAndSymbolString()) + [CtLiteralImpl]" to replay payment overages occurred during the contract ") + [CtInvocationImpl][CtVariableReadImpl]contract.getName());
            }
            [CtIfImpl][CtCommentImpl]// This relies on the mission being a Contract, and AtB to be on
            if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUseAtB()) [CtBlockImpl]{
                [CtInvocationImpl]setHasActiveContract();
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     * Calculate transit time for supplies based on what planet they are shipping from. To prevent extra
     * computation. This method does not calculate an exact jump path but rather determines the number of jumps
     * crudely by dividing distance in light years by 30 and then rounding up. Total part time is determined by
     * several by adding the following:
     * - (number of jumps - 1) * 7 days with a minimum value of zero.
     * - transit times from current planet and planet of supply origins in cases where the supply planet is not the same as current planet.
     * - a random 1d6 days for each jump plus 1d6 to simulate all of the other logistics of delivery.
     *
     * @param system
     * 		- A <code>PlanetarySystem</code> object where the supplies are shipping from
     * @return the number of days that supplies will take to arrive.
     */
    public [CtTypeReferenceImpl]int calculatePartTransitTime([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.universe.PlanetarySystem system) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// calculate number of jumps by light year distance as the crow flies divided by 30
        [CtCommentImpl]// the basic formula assumes 7 days per jump + system transit time on each side + random days equal
        [CtCommentImpl]// to (1 + number of jumps) d6
        [CtTypeReferenceImpl]double distance = [CtInvocationImpl][CtVariableReadImpl]system.getDistanceTo([CtInvocationImpl]getCurrentSystem());
        [CtLocalVariableImpl][CtCommentImpl]// calculate number of jumps by dividing by 30
        [CtTypeReferenceImpl]int jumps = [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtBinaryOperatorImpl][CtVariableReadImpl]distance / [CtLiteralImpl]30.0)));
        [CtLocalVariableImpl][CtCommentImpl]// you need a recharge except for the first jump
        [CtTypeReferenceImpl]int recharges = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]jumps - [CtLiteralImpl]1, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtCommentImpl]// if you are delivering from the same planet then no transit times
        [CtTypeReferenceImpl]int currentTransitTime = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]distance > [CtLiteralImpl]0) ? [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtInvocationImpl][CtInvocationImpl]getCurrentSystem().getTimeToJumpPoint([CtLiteralImpl]1.0)))) : [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int originTransitTime = [CtConditionalImpl]([CtBinaryOperatorImpl][CtVariableReadImpl]distance > [CtLiteralImpl]0) ? [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.ceil([CtInvocationImpl][CtVariableReadImpl]system.getTimeToJumpPoint([CtLiteralImpl]1.0)))) : [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int amazonFreeShipping = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtBinaryOperatorImpl][CtLiteralImpl]1 + [CtVariableReadImpl]jumps);
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]recharges * [CtLiteralImpl]7) + [CtVariableReadImpl]currentTransitTime) + [CtVariableReadImpl]originTransitTime) + [CtVariableReadImpl]amazonFreeShipping;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * *
     * Calculate transit times based on the margin of success from an acquisition roll. The values here
     * are all based on what the user entered for the campaign options.
     *
     * @param mos
     * 		- an integer of the margin of success of an acquisition roll
     * @return the number of days that supplies will take to arrive.
     */
    public [CtTypeReferenceImpl]int calculatePartTransitTime([CtParameterImpl][CtTypeReferenceImpl]int mos) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nDice = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getNDiceTransitTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int time = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getConstantTransitTime();
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nDice > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]time += [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtVariableReadImpl]nDice);
        }
        [CtLocalVariableImpl][CtCommentImpl]// now step forward through the calendar
        [CtTypeReferenceImpl]java.time.LocalDate arrivalDate = [CtInvocationImpl]getLocalDate();
        [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUnitTransitTime()) {
            [CtCaseImpl]case [CtFieldReadImpl]CampaignOptions.TRANSIT_UNIT_MONTH :
                [CtAssignmentImpl][CtVariableWriteImpl]arrivalDate = [CtInvocationImpl][CtVariableReadImpl]arrivalDate.plusMonths([CtVariableReadImpl]time);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]CampaignOptions.TRANSIT_UNIT_WEEK :
                [CtAssignmentImpl][CtVariableWriteImpl]arrivalDate = [CtInvocationImpl][CtVariableReadImpl]arrivalDate.plusWeeks([CtVariableReadImpl]time);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]CampaignOptions.TRANSIT_UNIT_DAY :
            [CtCaseImpl]default :
                [CtAssignmentImpl][CtVariableWriteImpl]arrivalDate = [CtInvocationImpl][CtVariableReadImpl]arrivalDate.plusDays([CtVariableReadImpl]time);
                [CtBreakImpl]break;
        }
        [CtLocalVariableImpl][CtCommentImpl]// now adjust for MoS and minimums
        [CtTypeReferenceImpl]int mosBonus = [CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquireMosBonus() * [CtVariableReadImpl]mos;
        [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquireMosUnit()) {
            [CtCaseImpl]case [CtFieldReadImpl]CampaignOptions.TRANSIT_UNIT_MONTH :
                [CtAssignmentImpl][CtVariableWriteImpl]arrivalDate = [CtInvocationImpl][CtVariableReadImpl]arrivalDate.minusMonths([CtVariableReadImpl]mosBonus);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]CampaignOptions.TRANSIT_UNIT_WEEK :
                [CtAssignmentImpl][CtVariableWriteImpl]arrivalDate = [CtInvocationImpl][CtVariableReadImpl]arrivalDate.minusWeeks([CtVariableReadImpl]mosBonus);
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]CampaignOptions.TRANSIT_UNIT_DAY :
            [CtCaseImpl]default :
                [CtAssignmentImpl][CtVariableWriteImpl]arrivalDate = [CtInvocationImpl][CtVariableReadImpl]arrivalDate.minusDays([CtVariableReadImpl]mosBonus);
                [CtBreakImpl]break;
        }
        [CtLocalVariableImpl][CtCommentImpl]// now establish minimum date and if this is before
        [CtTypeReferenceImpl]java.time.LocalDate minimumDate = [CtInvocationImpl]getLocalDate();
        [CtSwitchImpl]switch ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquireMinimumTimeUnit()) {
            [CtCaseImpl]case [CtFieldReadImpl]CampaignOptions.TRANSIT_UNIT_MONTH :
                [CtAssignmentImpl][CtVariableWriteImpl]minimumDate = [CtInvocationImpl][CtVariableReadImpl]minimumDate.plusMonths([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquireMinimumTime());
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]CampaignOptions.TRANSIT_UNIT_WEEK :
                [CtAssignmentImpl][CtVariableWriteImpl]minimumDate = [CtInvocationImpl][CtVariableReadImpl]minimumDate.plusWeeks([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquireMinimumTime());
                [CtBreakImpl]break;
            [CtCaseImpl]case [CtFieldReadImpl]CampaignOptions.TRANSIT_UNIT_DAY :
            [CtCaseImpl]default :
                [CtAssignmentImpl][CtVariableWriteImpl]minimumDate = [CtInvocationImpl][CtVariableReadImpl]minimumDate.plusDays([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAcquireMinimumTime());
                [CtBreakImpl]break;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]arrivalDate.isBefore([CtVariableReadImpl]minimumDate)) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.toIntExact([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.time.temporal.ChronoUnit.[CtFieldReferenceImpl]DAYS.between([CtInvocationImpl]getLocalDate(), [CtVariableReadImpl]minimumDate));
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.toIntExact([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.time.temporal.ChronoUnit.[CtFieldReferenceImpl]DAYS.between([CtInvocationImpl]getLocalDate(), [CtVariableReadImpl]arrivalDate));
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * This returns a PartInventory object detailing the current count
     * for a part on hand, in transit, and ordered.
     *
     * @param part
     * 		A part to lookup its current inventory.
     * @return A PartInventory object detailing the current counts of
    the part on hand, in transit, and ordered.
     * @see mekhq.campaign.parts.PartInventory
     */
    public [CtTypeReferenceImpl]mekhq.campaign.parts.PartInventory getPartInventory([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part part) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.PartInventory inventory = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.parts.PartInventory();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nSupply = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nTransit = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p : [CtInvocationImpl]getParts()) [CtBlockImpl]{
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]p.isSpare()) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]part.isSamePartType([CtVariableReadImpl]p)) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]p.isPresent()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtCommentImpl]// ProtomekArmor and BaArmor are derived from Armor
                        [CtVariableWriteImpl]nSupply += [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (p)).getAmount();
                    } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]nSupply += [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (p)).getShots();
                    } else [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]nSupply += [CtInvocationImpl][CtVariableReadImpl]p.getQuantity();
                    }
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtCommentImpl]// ProtomekArmor and BaArmor are derived from Armor
                    [CtVariableWriteImpl]nTransit += [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (p)).getAmount();
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]nTransit += [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (p)).getShots();
                } else [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]nTransit += [CtInvocationImpl][CtVariableReadImpl]p.getQuantity();
                }
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]inventory.setSupply([CtVariableReadImpl]nSupply);
        [CtInvocationImpl][CtVariableReadImpl]inventory.setTransit([CtVariableReadImpl]nTransit);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nOrdered = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.work.IAcquisitionWork onOrder = [CtInvocationImpl][CtInvocationImpl]getShoppingList().getShoppingItem([CtVariableReadImpl]part);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]onOrder) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]onOrder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtCommentImpl]// ProtoMech Armor and BaArmor are derived from Armor
                [CtVariableWriteImpl]nOrdered += [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.Armor) (onOrder)).getAmount();
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]onOrder instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]nOrdered += [CtInvocationImpl][CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) (onOrder)).getShots();
            } else [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]nOrdered += [CtInvocationImpl][CtVariableReadImpl]onOrder.getQuantity();
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]inventory.setOrdered([CtVariableReadImpl]nOrdered);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String countModifier = [CtLiteralImpl]"";
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Armor) [CtBlockImpl]{
            [CtAssignmentImpl][CtCommentImpl]// ProtoMech Armor and BaArmor are derived from Armor
            [CtVariableWriteImpl]countModifier = [CtLiteralImpl]"points";
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]part instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.AmmoStorage) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]countModifier = [CtLiteralImpl]"shots";
        }
        [CtInvocationImpl][CtVariableReadImpl]inventory.setCountModifier([CtVariableReadImpl]countModifier);
        [CtReturnImpl]return [CtVariableReadImpl]inventory;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getTotalEquipmentValue() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money unitsSellValue = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnitCosts([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getSellValue);
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]streamSpareParts().map([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Part::getActualValue).reduce([CtVariableReadImpl]unitsSellValue, [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Money::plus);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Calculate the total value of units in the TO&E. This serves as the basis for contract payments in the StellarOps
     * Beta.
     *
     * @return  */
    public [CtTypeReferenceImpl]mekhq.campaign.Money getForceValue() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getForceValue([CtLiteralImpl]false);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Calculate the total value of units in the TO&E. This serves as the basis for contract payments in the StellarOps
     * Beta.
     *
     * @return  */
    public [CtTypeReferenceImpl]mekhq.campaign.Money getForceValue([CtParameterImpl][CtTypeReferenceImpl]boolean noInfantry) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money value = [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID uuid : [CtInvocationImpl][CtFieldReadImpl]forces.getAllUnits([CtLiteralImpl]false)) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u = [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtVariableReadImpl]uuid);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null == [CtVariableReadImpl]u) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noInfantry && [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().getEntityType() & [CtFieldReadImpl]Entity.ETYPE_INFANTRY) == [CtFieldReadImpl]Entity.ETYPE_INFANTRY)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().getEntityType() & [CtFieldReadImpl]Entity.ETYPE_BATTLEARMOR) == [CtFieldReadImpl]Entity.ETYPE_BATTLEARMOR))) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_DROPSHIP)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getDropshipContractPercent() == [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]value.plus([CtInvocationImpl]getEquipmentContractValue([CtVariableReadImpl]u, [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useEquipmentContractSaleValue()));
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_WARSHIP)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getWarshipContractPercent() == [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]value.plus([CtInvocationImpl]getEquipmentContractValue([CtVariableReadImpl]u, [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useEquipmentContractSaleValue()));
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_JUMPSHIP) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_SPACE_STATION)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getJumpshipContractPercent() == [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]value.plus([CtInvocationImpl]getEquipmentContractValue([CtVariableReadImpl]u, [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useEquipmentContractSaleValue()));
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]value.plus([CtInvocationImpl]getEquipmentContractValue([CtVariableReadImpl]u, [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useEquipmentContractSaleValue()));
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]value;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getEquipmentContractValue([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u, [CtParameterImpl][CtTypeReferenceImpl]boolean useSaleValue) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money value;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money percentValue;
        [CtIfImpl]if ([CtVariableReadImpl]useSaleValue) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]u.getSellValue();
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]value = [CtInvocationImpl][CtVariableReadImpl]u.getBuyCost();
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_DROPSHIP)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]percentValue = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]value.multipliedBy([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getDropshipContractPercent()).dividedBy([CtLiteralImpl]100);
        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_WARSHIP)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]percentValue = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]value.multipliedBy([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getWarshipContractPercent()).dividedBy([CtLiteralImpl]100);
        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_JUMPSHIP) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasETypeFlag([CtTypeAccessImpl]Entity.ETYPE_SPACE_STATION)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]percentValue = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]value.multipliedBy([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getJumpshipContractPercent()).dividedBy([CtLiteralImpl]100);
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]percentValue = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]value.multipliedBy([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getEquipmentContractPercent()).dividedBy([CtLiteralImpl]100);
        }
        [CtReturnImpl]return [CtVariableReadImpl]percentValue;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getContractBase() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().usePeacetimeCost()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getPeacetimeCost().multipliedBy([CtLiteralImpl]0.75).plus([CtInvocationImpl]getForceValue([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useInfantryDontCount()));
        } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useEquipmentContractBase()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getForceValue([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useInfantryDontCount());
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getTheoreticalPayroll([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useInfantryDontCount());
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void addLoan([CtParameterImpl][CtTypeReferenceImpl]Loan loan) [CtBlockImpl]{
        [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"You have taken out loan " + [CtInvocationImpl][CtVariableReadImpl]loan.getDescription()) + [CtLiteralImpl]". Your account has been credited ") + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]loan.getPrincipal().toAmountAndSymbolString()) + [CtLiteralImpl]" for the principal amount.");
        [CtInvocationImpl][CtFieldReadImpl]finances.addLoan([CtVariableReadImpl]loan);
        [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.LoanNewEvent([CtVariableReadImpl]loan));
        [CtInvocationImpl][CtFieldReadImpl]finances.credit([CtInvocationImpl][CtVariableReadImpl]loan.getPrincipal(), [CtTypeAccessImpl]Transaction.C_LOAN_PRINCIPAL, [CtBinaryOperatorImpl][CtLiteralImpl]"loan principal for " + [CtInvocationImpl][CtVariableReadImpl]loan.getDescription(), [CtInvocationImpl]getLocalDate());
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void payOffLoan([CtParameterImpl][CtTypeReferenceImpl]Loan loan) [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]finances.debit([CtInvocationImpl][CtVariableReadImpl]loan.getRemainingValue(), [CtTypeAccessImpl]Transaction.C_LOAN_PAYMENT, [CtBinaryOperatorImpl][CtLiteralImpl]"loan payoff for " + [CtInvocationImpl][CtVariableReadImpl]loan.getDescription(), [CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"You have paid off the remaining loan balance of " + [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]loan.getRemainingValue().toAmountAndSymbolString()) + [CtLiteralImpl]"on ") + [CtInvocationImpl][CtVariableReadImpl]loan.getDescription());
            [CtInvocationImpl][CtFieldReadImpl]finances.removeLoan([CtVariableReadImpl]loan);
            [CtInvocationImpl][CtTypeAccessImpl]MekHQ.triggerEvent([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.event.LoanPaidEvent([CtVariableReadImpl]loan));
        } else [CtBlockImpl]{
            [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='red'>You do not have enough funds to pay off " + [CtInvocationImpl][CtVariableReadImpl]loan.getDescription()) + [CtLiteralImpl]"</font>");
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.FinancialReport getFinancialReport() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]FinancialReport.calculate([CtThisAccessImpl]this);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getFormattedFinancialReport() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder();
        [CtLocalVariableImpl][CtTypeReferenceImpl]FinancialReport r = [CtInvocationImpl]getFinancialReport();
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money liabilities = [CtInvocationImpl][CtVariableReadImpl]r.getTotalLiabilities();
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money assets = [CtInvocationImpl][CtVariableReadImpl]r.getTotalAssets();
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money netWorth = [CtInvocationImpl][CtVariableReadImpl]r.getNetWorth();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int longest = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]liabilities.toAmountAndSymbolString().length(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]assets.toAmountAndSymbolString().length());
        [CtAssignmentImpl][CtVariableWriteImpl]longest = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]netWorth.toAmountAndSymbolString().length(), [CtVariableReadImpl]longest);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String formatted = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"%1$" + [CtVariableReadImpl]longest) + [CtLiteralImpl]"s";
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"Net Worth................ ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtVariableReadImpl]netWorth.toAmountAndSymbolString())).append([CtLiteralImpl]"\n\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"    Assets............... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtVariableReadImpl]assets.toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       Cash.............. ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getCash().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMechValue().isPositive()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       Mechs............. ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMechValue().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getVeeValue().isPositive()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       Vehicles.......... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getVeeValue().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getBattleArmorValue().isPositive()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       BattleArmor....... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getBattleArmorValue().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getInfantryValue().isPositive()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       Infantry.......... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getInfantryValue().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getProtomechValue().isPositive()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       Protomechs........ ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getProtomechValue().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getSmallCraftValue().isPositive()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       Small Craft....... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getSmallCraftValue().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getLargeCraftValue().isPositive()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       Large Craft....... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getLargeCraftValue().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       Spare Parts....... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getSparePartsValue().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getFinances().getAllAssets().size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Asset asset : [CtInvocationImpl][CtInvocationImpl]getFinances().getAllAssets()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String assetName = [CtInvocationImpl][CtVariableReadImpl]asset.getName();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]assetName.length() > [CtLiteralImpl]18) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]assetName = [CtInvocationImpl][CtVariableReadImpl]assetName.substring([CtLiteralImpl]0, [CtLiteralImpl]17);
                } else [CtBlockImpl]{
                    [CtLocalVariableImpl][CtTypeReferenceImpl]int numPeriods = [CtBinaryOperatorImpl][CtLiteralImpl]18 - [CtInvocationImpl][CtVariableReadImpl]assetName.length();
                    [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtVariableReadImpl]numPeriods; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]assetName += [CtLiteralImpl]".";
                    }
                }
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]assetName += [CtLiteralImpl]" ";
                [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       ").append([CtVariableReadImpl]assetName).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]asset.getValue().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
            }
        }
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"    Liabilities.......... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtVariableReadImpl]liabilities.toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"       Loans............. ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getLoans().toAmountAndSymbolString())).append([CtLiteralImpl]"\n\n\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"Monthly Profit........... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMonthlyIncome().minus([CtInvocationImpl][CtVariableReadImpl]r.getMonthlyExpenses()).toAmountAndSymbolString())).append([CtLiteralImpl]"\n\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"Monthly Income........... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMonthlyIncome().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"    Contract Payments.... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getContracts().toAmountAndSymbolString())).append([CtLiteralImpl]"\n\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"Monthly Expenses......... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMonthlyExpenses().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"    Salaries............. ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getSalaries().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"    Maintenance.......... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMaintenance().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"    Overhead............. ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getOverheadCosts().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]campaignOptions.usePeacetimeCost()) [CtBlockImpl]{
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"    Spare Parts.......... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMonthlySparePartCosts().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"    Training Munitions... ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMonthlyAmmoCosts().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
            [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"    Fuel................. ").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtVariableReadImpl]formatted, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]r.getMonthlyFuelCosts().toAmountAndSymbolString())).append([CtLiteralImpl]"\n");
        }
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setHealingTimeOptions([CtParameterImpl][CtTypeReferenceImpl]int newHeal, [CtParameterImpl][CtTypeReferenceImpl]int newNaturalHeal) [CtBlockImpl]{
        [CtLocalVariableImpl][CtCommentImpl]// we need to check the current values and then if necessary change the
        [CtCommentImpl]// times for all
        [CtCommentImpl]// personnel, giving them credit for their current waiting time
        [CtTypeReferenceImpl]int currentHeal = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getHealingWaitingPeriod();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int currentNaturalHeal = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getNaturalHealingWaitingPeriod();
        [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().setHealingWaitingPeriod([CtVariableReadImpl]newHeal);
        [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().setNaturalHealingWaitingPeriod([CtVariableReadImpl]newNaturalHeal);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int healDiff = [CtBinaryOperatorImpl][CtVariableReadImpl]newHeal - [CtVariableReadImpl]currentHeal;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int naturalDiff = [CtBinaryOperatorImpl][CtVariableReadImpl]newNaturalHeal - [CtVariableReadImpl]currentNaturalHeal;
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]healDiff != [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtVariableReadImpl]naturalDiff != [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getDoctorId() != [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]p.setDaysToWaitForHealing([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getDaysToWaitForHealing() + [CtVariableReadImpl]healDiff, [CtLiteralImpl]1));
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]p.setDaysToWaitForHealing([CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getDaysToWaitForHealing() + [CtVariableReadImpl]naturalDiff, [CtLiteralImpl]1));
                }
            }
        }
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns our list of potential transport ships
     *
     * @return  */
    public [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.util.UUID> getTransportShips() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]transportShips;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTotalMechBays() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getMechCapacity).sum())));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTotalASFBays() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getASFCapacity).sum())));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTotalSmallCraftBays() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getSmallCraftCapacity).sum())));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTotalBattleArmorBays() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getBattleArmorCapacity).sum())));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTotalInfantryBays() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getInfantryCapacity).sum())));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTotalHeavyVehicleBays() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getHeavyVehicleCapacity).sum())));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTotalLightVehicleBays() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getLightVehicleCapacity).sum())));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTotalProtomechBays() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]int) ([CtTypeAccessImpl]java.lang.Math.round([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getProtomechCapacity).sum())));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getTotalDockingCollars() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().filter([CtLambdaImpl]([CtParameterImpl] u) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity() instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Jumpship).mapToInt([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getDocks).sum();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getTotalInsulatedCargoCapacity() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getInsulatedCargoCapacity).sum();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getTotalRefrigeratedCargoCapacity() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getRefrigeratedCargoCapacity).sum();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getTotalLivestockCargoCapacity() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getLivestockCargoCapacity).sum();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getTotalLiquidCargoCapacity() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getLiquidCargoCapacity).sum();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getTotalCargoCapacity() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnitsStream().mapToDouble([CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getCargoCapacity).sum();
    }

    [CtMethodImpl][CtCommentImpl]// Liquid not included
    public [CtTypeReferenceImpl]double getTotalCombinedCargoCapacity() [CtBlockImpl]{
        [CtReturnImpl]return [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl]getTotalCargoCapacity() + [CtInvocationImpl]getTotalLivestockCargoCapacity()) + [CtInvocationImpl]getTotalInsulatedCargoCapacity()) + [CtInvocationImpl]getTotalRefrigeratedCargoCapacity();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getNumberOfUnitsByType([CtParameterImpl][CtTypeReferenceImpl]long type) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getNumberOfUnitsByType([CtVariableReadImpl]type, [CtLiteralImpl]false, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getNumberOfUnitsByType([CtParameterImpl][CtTypeReferenceImpl]long type, [CtParameterImpl][CtTypeReferenceImpl]boolean inTransit) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getNumberOfUnitsByType([CtVariableReadImpl]type, [CtVariableReadImpl]inTransit, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getNumberOfUnitsByType([CtParameterImpl][CtTypeReferenceImpl]long type, [CtParameterImpl][CtTypeReferenceImpl]boolean inTransit, [CtParameterImpl][CtTypeReferenceImpl]boolean lv) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int num = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]inTransit) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]unit.isPresent())) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]unit.isMothballed()) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]mekhq.campaign.unit.Unit.ETYPE_MOTHBALLED) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                }
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]unit.getEntity();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]GunEmplacement) || [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]FighterSquadron)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Jumpship)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_MECH) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Mech)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_DROPSHIP) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Dropship)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_SMALL_CRAFT) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SmallCraft)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Dropship))) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_CONV_FIGHTER) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ConvFighter)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_AERO) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Aero)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SmallCraft) || [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ConvFighter)))) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_INFANTRY) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Infantry)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BattleArmor))) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_BATTLEARMOR) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BattleArmor)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_TANK) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Tank)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.getWeight() <= [CtLiteralImpl]50) && [CtVariableReadImpl]lv) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.getWeight() > [CtLiteralImpl]50) && [CtUnaryOperatorImpl](![CtVariableReadImpl]lv))) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                }
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_PROTOMECH) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Protomech)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
            }
        }
        [CtReturnImpl]return [CtVariableReadImpl]num;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]double getCargoTonnage([CtParameterImpl][CtTypeReferenceImpl]boolean inTransit) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getCargoTonnage([CtVariableReadImpl]inTransit, [CtLiteralImpl]false);
    }

    [CtMethodImpl][CtCommentImpl]// FIXME: This whole method needs re-worked once Dropship Assignments are in
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
    public [CtTypeReferenceImpl]double getCargoTonnage([CtParameterImpl]final [CtTypeReferenceImpl]boolean inTransit, [CtParameterImpl]final [CtTypeReferenceImpl]boolean mothballed) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]double cargoTonnage = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double mothballedTonnage = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int mechs = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_MECH);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int ds = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_DROPSHIP);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int sc = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int cf = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_CONV_FIGHTER);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int asf = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_AERO);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int inf = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_INFANTRY);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int ba = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_BATTLEARMOR);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int lv = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_TANK, [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int hv = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_TANK, [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int protos = [CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_PROTOMECH);
        [CtOperatorAssignmentImpl][CtVariableWriteImpl]cargoTonnage += [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]streamSpareParts().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtVariableReadImpl]inTransit || [CtInvocationImpl][CtVariableReadImpl]p.isPresent()).mapToDouble([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getQuantity() * [CtInvocationImpl][CtVariableReadImpl]p.getTonnage()).sum();
        [CtForEachImpl][CtCommentImpl]// place units in bays
        [CtCommentImpl]// FIXME: This has been temporarily disabled. It really needs DropShip assignments done to fix it correctly.
        [CtCommentImpl]// Remaining units go into cargo
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtVariableReadImpl]inTransit) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]unit.isPresent())) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]unit.getEntity();
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]unit.isMothballed()) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]mothballedTonnage += [CtInvocationImpl][CtVariableReadImpl]en.getWeight();
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]GunEmplacement) || [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]FighterSquadron)) || [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Jumpship)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtCommentImpl]// cargoTonnage += en.getWeight();
        }
        [CtIfImpl]if ([CtVariableReadImpl]mothballed) [CtBlockImpl]{
            [CtReturnImpl]return [CtVariableReadImpl]mothballedTonnage;
        }
        [CtReturnImpl]return [CtVariableReadImpl]cargoTonnage;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCargoDetails() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuffer sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuffer([CtLiteralImpl]"Cargo\n\n");
        [CtLocalVariableImpl][CtTypeReferenceImpl]double ccc = [CtInvocationImpl][CtThisAccessImpl]this.getTotalCombinedCargoCapacity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double gcc = [CtInvocationImpl][CtThisAccessImpl]this.getTotalCargoCapacity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double icc = [CtInvocationImpl][CtThisAccessImpl]this.getTotalInsulatedCargoCapacity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double lcc = [CtInvocationImpl][CtThisAccessImpl]this.getTotalLiquidCargoCapacity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double scc = [CtInvocationImpl][CtThisAccessImpl]this.getTotalLivestockCargoCapacity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double rcc = [CtInvocationImpl][CtThisAccessImpl]this.getTotalRefrigeratedCargoCapacity();
        [CtLocalVariableImpl][CtTypeReferenceImpl]double tonnage = [CtInvocationImpl][CtThisAccessImpl]this.getCargoTonnage([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]double mothballedTonnage = [CtInvocationImpl][CtThisAccessImpl]this.getCargoTonnage([CtLiteralImpl]false, [CtLiteralImpl]true);
        [CtLocalVariableImpl][CtTypeReferenceImpl]double mothballedUnits = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Unit.ETYPE_MOTHBALLED), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]double combined = [CtBinaryOperatorImpl][CtVariableReadImpl]tonnage + [CtVariableReadImpl]mothballedTonnage;
        [CtLocalVariableImpl][CtTypeReferenceImpl]double transported = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtVariableReadImpl]combined, [CtVariableReadImpl]ccc);
        [CtLocalVariableImpl][CtTypeReferenceImpl]double overage = [CtBinaryOperatorImpl][CtVariableReadImpl]combined - [CtVariableReadImpl]transported;
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %6.3f\n", [CtLiteralImpl]"Total Capacity:", [CtVariableReadImpl]ccc));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %6.3f\n", [CtLiteralImpl]"General Capacity:", [CtVariableReadImpl]gcc));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %6.3f\n", [CtLiteralImpl]"Insulated Capacity:", [CtVariableReadImpl]icc));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %6.3f\n", [CtLiteralImpl]"Liquid Capacity:", [CtVariableReadImpl]lcc));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %6.3f\n", [CtLiteralImpl]"Livestock Capacity:", [CtVariableReadImpl]scc));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %6.3f\n", [CtLiteralImpl]"Refrigerated Capacity:", [CtVariableReadImpl]rcc));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %6.3f\n", [CtLiteralImpl]"Cargo Transported:", [CtVariableReadImpl]tonnage));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4s (%1.0f)\n", [CtLiteralImpl]"Mothballed Units as Cargo (Tons):", [CtVariableReadImpl]mothballedUnits, [CtVariableReadImpl]mothballedTonnage));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %6.3f/%1.3f\n", [CtLiteralImpl]"Transported/Capacity:", [CtVariableReadImpl]transported, [CtVariableReadImpl]ccc));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %6.3f\n", [CtLiteralImpl]"Overage Not Transported:", [CtVariableReadImpl]overage));
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtVariableReadImpl]sb);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getOccupiedBays([CtParameterImpl][CtTypeReferenceImpl]long type) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getOccupiedBays([CtVariableReadImpl]type, [CtLiteralImpl]false);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]int getOccupiedBays([CtParameterImpl][CtTypeReferenceImpl]long type, [CtParameterImpl][CtTypeReferenceImpl]boolean lv) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int num = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit unit : [CtInvocationImpl]getUnits()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]unit.isMothballed()) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]Entity en = [CtInvocationImpl][CtVariableReadImpl]unit.getEntity();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]GunEmplacement) || [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Jumpship)) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_MECH) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Mech)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_DROPSHIP) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Dropship)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_SMALL_CRAFT) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SmallCraft)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Dropship))) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_CONV_FIGHTER) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ConvFighter)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_AERO) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Aero)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]SmallCraft) || [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]ConvFighter)))) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_INFANTRY) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Infantry)) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BattleArmor))) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_BATTLEARMOR) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]BattleArmor)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_TANK) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Tank)) [CtBlockImpl]{
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.getWeight() <= [CtLiteralImpl]50) && [CtVariableReadImpl]lv) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]en.getWeight() > [CtLiteralImpl]50) && [CtUnaryOperatorImpl](![CtVariableReadImpl]lv))) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
                }
                [CtContinueImpl]continue;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_PROTOMECH) && [CtBinaryOperatorImpl]([CtVariableReadImpl]en instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Protomech)) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]num++;
            }
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_MECH) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl]getTotalMechBays(), [CtVariableReadImpl]num);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_AERO) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl]getTotalASFBays(), [CtVariableReadImpl]num);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_INFANTRY) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl]getTotalInfantryBays(), [CtVariableReadImpl]num);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_BATTLEARMOR) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl]getTotalBattleArmorBays(), [CtVariableReadImpl]num);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_TANK) [CtBlockImpl]{
            [CtIfImpl]if ([CtVariableReadImpl]lv) [CtBlockImpl]{
                [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl]getTotalLightVehicleBays(), [CtVariableReadImpl]num);
            }
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl]getTotalHeavyVehicleBays(), [CtVariableReadImpl]num);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_SMALL_CRAFT) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl]getTotalSmallCraftBays(), [CtVariableReadImpl]num);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_PROTOMECH) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl]getTotalProtomechBays(), [CtVariableReadImpl]num);
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]type == [CtFieldReadImpl]Entity.ETYPE_DROPSHIP) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.min([CtInvocationImpl]getTotalDockingCollars(), [CtVariableReadImpl]num);
        }
        [CtReturnImpl]return [CtUnaryOperatorImpl]-[CtLiteralImpl]1;[CtCommentImpl]// default, this is an error condition

    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTransportDetails() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noMech = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_MECH) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_MECH), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noDS = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_DROPSHIP) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_DROPSHIP), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noSC = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtCommentImpl]// FIXME: What type of bays do ConvFighters use?
        [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
        [CtTypeReferenceImpl]int noCF = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_CONV_FIGHTER) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_CONV_FIGHTER), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noASF = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_AERO) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_AERO), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nolv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_TANK, [CtLiteralImpl]false, [CtLiteralImpl]true) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_TANK, [CtLiteralImpl]true), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int nohv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_TANK) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_TANK), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noinf = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_INFANTRY) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_INFANTRY), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int noBA = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_BATTLEARMOR) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_BATTLEARMOR), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtCommentImpl]// FIXME: This should be used somewhere...
        [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unused")
        [CtTypeReferenceImpl]int noProto = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Entity.ETYPE_PROTOMECH) - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_PROTOMECH), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int freehv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getTotalHeavyVehicleBays() - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_TANK), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int freeinf = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getTotalInfantryBays() - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_INFANTRY), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int freeba = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getTotalBattleArmorBays() - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_BATTLEARMOR), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int freeSC = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtInvocationImpl]getTotalSmallCraftBays() - [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int mothballedAsCargo = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtInvocationImpl]getNumberOfUnitsByType([CtTypeAccessImpl]Unit.ETYPE_MOTHBALLED), [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String asfAppend = [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newNoASF = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]noASF - [CtVariableReadImpl]freeSC, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int placedASF = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]noASF - [CtVariableReadImpl]newNoASF, [CtLiteralImpl]0);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noASF > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]freeSC > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]asfAppend = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]" [" + [CtVariableReadImpl]placedASF) + [CtLiteralImpl]" ASF will be placed in Small Craft bays]";
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]freeSC -= [CtVariableReadImpl]placedASF;
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String lvAppend = [CtLiteralImpl]"";
        [CtLocalVariableImpl][CtTypeReferenceImpl]int newNolv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nolv - [CtVariableReadImpl]freehv, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int placedlv = [CtInvocationImpl][CtTypeAccessImpl]java.lang.Math.max([CtBinaryOperatorImpl][CtVariableReadImpl]nolv - [CtVariableReadImpl]newNolv, [CtLiteralImpl]0);
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]nolv > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]freehv > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]lvAppend = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]" [" + [CtVariableReadImpl]placedlv) + [CtLiteralImpl]" Light Vehicles will be placed in Heavy Vehicle bays]";
            [CtOperatorAssignmentImpl][CtVariableWriteImpl]freehv -= [CtVariableReadImpl]placedlv;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noBA > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]freeinf > [CtLiteralImpl]0)) [CtBlockImpl]{
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noinf > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]freeba > [CtLiteralImpl]0)) [CtBlockImpl]{
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuffer sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuffer([CtLiteralImpl]"Transports\n\n");
        [CtInvocationImpl][CtCommentImpl]// Lets do Mechs first.
        [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d (%4d)      %-35s     %4d\n", [CtLiteralImpl]"Mech Bays (Occupied):", [CtInvocationImpl]getTotalMechBays(), [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_MECH), [CtLiteralImpl]"Mechs Not Transported:", [CtVariableReadImpl]noMech));
        [CtInvocationImpl][CtCommentImpl]// Lets do ASF next.
        [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d (%4d)      %-35s     %4d%s\n", [CtLiteralImpl]"ASF Bays (Occupied):", [CtInvocationImpl]getTotalASFBays(), [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_AERO), [CtLiteralImpl]"ASF Not Transported:", [CtVariableReadImpl]noASF, [CtVariableReadImpl]asfAppend));
        [CtInvocationImpl][CtCommentImpl]// Lets do Light Vehicles next.
        [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d (%4d)      %-35s     %4d%s\n", [CtLiteralImpl]"Light Vehicle Bays (Occupied):", [CtInvocationImpl]getTotalLightVehicleBays(), [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_TANK, [CtLiteralImpl]true), [CtLiteralImpl]"Light Vehicles Not Transported:", [CtVariableReadImpl]nolv, [CtVariableReadImpl]lvAppend));
        [CtInvocationImpl][CtCommentImpl]// Lets do Heavy Vehicles next.
        [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d (%4d)      %-35s     %4d\n", [CtLiteralImpl]"Heavy Vehicle Bays (Occupied):", [CtInvocationImpl]getTotalHeavyVehicleBays(), [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_TANK), [CtLiteralImpl]"Heavy Vehicles Not Transported:", [CtVariableReadImpl]nohv));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noASF > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]freeSC > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Lets do ASF in Free Small Craft Bays next.
            [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s   %4d (%4d)      %-35s     %4d\n", [CtLiteralImpl]"   Light Vehicles in Heavy Vehicle Bays (Occupied):", [CtInvocationImpl]getTotalHeavyVehicleBays(), [CtBinaryOperatorImpl][CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_TANK) + [CtVariableReadImpl]placedlv, [CtLiteralImpl]"Light Vehicles Not Transported:", [CtVariableReadImpl]newNolv));
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]nolv > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]freehv > [CtLiteralImpl]0)) [CtBlockImpl]{
        }
        [CtInvocationImpl][CtCommentImpl]// Lets do Infantry next.
        [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d (%4d)      %-35s     %4d\n", [CtLiteralImpl]"Infantry Bays (Occupied):", [CtInvocationImpl]getTotalInfantryBays(), [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_INFANTRY), [CtLiteralImpl]"Infantry Not Transported:", [CtVariableReadImpl]noinf));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noBA > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]freeinf > [CtLiteralImpl]0)) [CtBlockImpl]{
        }
        [CtInvocationImpl][CtCommentImpl]// Lets do Battle Armor next.
        [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d (%4d)      %-35s     %4d\n", [CtLiteralImpl]"Battle Armor Bays (Occupied):", [CtInvocationImpl]getTotalBattleArmorBays(), [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_BATTLEARMOR), [CtLiteralImpl]"Battle Armor Not Transported:", [CtVariableReadImpl]noBA));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noinf > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]freeba > [CtLiteralImpl]0)) [CtBlockImpl]{
        }
        [CtInvocationImpl][CtCommentImpl]// Lets do Small Craft next.
        [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d (%4d)      %-35s     %4d\n", [CtLiteralImpl]"Small Craft Bays (Occupied):", [CtInvocationImpl]getTotalSmallCraftBays(), [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT), [CtLiteralImpl]"Small Craft Not Transported:", [CtVariableReadImpl]noSC));
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]noASF > [CtLiteralImpl]0) && [CtBinaryOperatorImpl]([CtVariableReadImpl]freeSC > [CtLiteralImpl]0)) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// Lets do ASF in Free Small Craft Bays next.
            [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s   %4d (%4d)      %-35s     %4d\n", [CtLiteralImpl]"   ASF in Small Craft Bays (Occupied):", [CtInvocationImpl]getTotalSmallCraftBays(), [CtBinaryOperatorImpl][CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_SMALL_CRAFT) + [CtVariableReadImpl]placedASF, [CtLiteralImpl]"ASF Not Transported:", [CtVariableReadImpl]newNoASF));
        }
        [CtInvocationImpl][CtCommentImpl]// Lets do Protomechs next.
        [CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d (%4d)      %-35s     %4d\n", [CtLiteralImpl]"Protomech Bays (Occupied):", [CtInvocationImpl]getTotalProtomechBays(), [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_PROTOMECH), [CtLiteralImpl]"Protomechs Not Transported:", [CtVariableReadImpl]noSC));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n\n");
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d (%4d)      %-35s     %4d\n", [CtLiteralImpl]"Docking Collars (Occupied):", [CtInvocationImpl]getTotalDockingCollars(), [CtInvocationImpl]getOccupiedBays([CtTypeAccessImpl]Entity.ETYPE_DROPSHIP), [CtLiteralImpl]"Dropships Not Transported:", [CtVariableReadImpl]noDS));
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n\n");
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-35s      %4d\n", [CtLiteralImpl]"Mothballed Units (see Cargo report)", [CtVariableReadImpl]mothballedAsCargo));
        [CtReturnImpl]return [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.String([CtVariableReadImpl]sb);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getCombatPersonnelDetails() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]int[] countPersonByType = [CtNewArrayImpl]new [CtTypeReferenceImpl]int[[CtFieldReadImpl]Person.T_NUM];
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countTotal = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countInjured = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countMIA = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countKIA = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countDead = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countRetired = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money salary = [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.hasPrimaryCombatRole()) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus().isFree())) [CtBlockImpl]{
                [CtContinueImpl]continue;
            }
            [CtIfImpl][CtCommentImpl]// Add them to the total count
            if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isActive()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtArrayWriteImpl][CtVariableReadImpl]countPersonByType[[CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole()]++;
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countTotal++;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useAdvancedMedical() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getInjuries().size() > [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]countInjured++;
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getHits() > [CtLiteralImpl]0) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]countInjured++;
                }
                [CtAssignmentImpl][CtVariableWriteImpl]salary = [CtInvocationImpl][CtVariableReadImpl]salary.plus([CtInvocationImpl][CtVariableReadImpl]p.getSalary());
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isRetired()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countRetired++;
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isMIA()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countMIA++;
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isKIA()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countKIA++;
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countDead++;
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isDead()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countDead++;
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]"Combat Personnel\n\n");
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"Total Combat Personnel", [CtVariableReadImpl]countTotal));
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl]Person.T_NUM; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]Person.isCombatRole([CtVariableReadImpl]i)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"    %-30s    %4s\n", [CtInvocationImpl][CtTypeAccessImpl]Person.getRoleDesc([CtVariableReadImpl]i, [CtInvocationImpl][CtInvocationImpl]getFaction().isClan()), [CtArrayReadImpl][CtVariableReadImpl]countPersonByType[[CtVariableReadImpl]i]));
            }
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"Injured Combat Personnel", [CtVariableReadImpl]countInjured)).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"MIA Combat Personnel", [CtVariableReadImpl]countMIA)).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"KIA Combat Personnel", [CtVariableReadImpl]countKIA)).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"Retired Combat Personnel", [CtVariableReadImpl]countRetired)).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"Dead Combat Personnel", [CtVariableReadImpl]countDead)).append([CtLiteralImpl]"\nMonthly Salary For Combat Personnel: ").append([CtInvocationImpl][CtVariableReadImpl]salary.toAmountAndSymbolString());
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getSupportPersonnelDetails() [CtBlockImpl]{
        [CtLocalVariableImpl][CtArrayTypeReferenceImpl]int[] countPersonByType = [CtNewArrayImpl]new [CtTypeReferenceImpl]int[[CtFieldReadImpl]Person.T_NUM];
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countTotal = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countInjured = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countMIA = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countKIA = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countDead = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int countRetired = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money salary = [CtInvocationImpl][CtTypeAccessImpl]Money.zero();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int prisoners = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int bondsmen = [CtLiteralImpl]0;
        [CtLocalVariableImpl][CtTypeReferenceImpl]int dependents = [CtLiteralImpl]0;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
            [CtLocalVariableImpl][CtCommentImpl]// Add them to the total count
            [CtTypeReferenceImpl]boolean primarySupport = [CtInvocationImpl][CtVariableReadImpl]p.hasPrimarySupportRole([CtLiteralImpl]false);
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]primarySupport && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus().isFree()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isActive()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtArrayWriteImpl][CtVariableReadImpl]countPersonByType[[CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole()]++;
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countTotal++;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getInjuries().size() > [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getHits() > [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]countInjured++;
                }
                [CtAssignmentImpl][CtVariableWriteImpl]salary = [CtInvocationImpl][CtVariableReadImpl]salary.plus([CtInvocationImpl][CtVariableReadImpl]p.getSalary());
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus().isPrisoner() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isActive()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]prisoners++;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getInjuries().size() > [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getHits() > [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]countInjured++;
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus().isBondsman() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isActive()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]bondsmen++;
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getInjuries().size() > [CtLiteralImpl]0) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getHits() > [CtLiteralImpl]0)) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]countInjured++;
                }
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]primarySupport && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isRetired()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countRetired++;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]primarySupport && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isMIA()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countMIA++;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]primarySupport && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isKIA()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countKIA++;
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countDead++;
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]primarySupport && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isDead()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]countDead++;
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.isDependent() && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getStatus().isActive()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus().isFree()) [CtBlockImpl]{
                [CtUnaryOperatorImpl][CtVariableWriteImpl]dependents++;
            }
        }
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder sb = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtLiteralImpl]"Support Personnel\n\n");
        [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"Total Support Personnel", [CtVariableReadImpl]countTotal));
        [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl]Person.T_NUM; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]Person.isSupportRole([CtVariableReadImpl]i)) [CtBlockImpl]{
                [CtInvocationImpl][CtVariableReadImpl]sb.append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"    %-30s    %4s\n", [CtInvocationImpl][CtTypeAccessImpl]Person.getRoleDesc([CtVariableReadImpl]i, [CtInvocationImpl][CtInvocationImpl]getFaction().isClan()), [CtArrayReadImpl][CtVariableReadImpl]countPersonByType[[CtVariableReadImpl]i]));
            }
        }
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]sb.append([CtLiteralImpl]"\n").append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"Injured Support Personnel", [CtVariableReadImpl]countInjured)).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"MIA Support Personnel", [CtVariableReadImpl]countMIA)).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"KIA Support Personnel", [CtVariableReadImpl]countKIA)).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"Retired Support Personnel", [CtVariableReadImpl]countRetired)).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtLiteralImpl]"%-30s        %4s\n", [CtLiteralImpl]"Dead Support Personnel", [CtVariableReadImpl]countDead)).append([CtLiteralImpl]"\nMonthly Salary For Support Personnel: ").append([CtInvocationImpl][CtVariableReadImpl]salary.toAmountAndSymbolString()).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"\nYou have " + [CtVariableReadImpl]dependents) + [CtLiteralImpl]" %s", [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]dependents == [CtLiteralImpl]1 ? [CtLiteralImpl]"dependent" : [CtLiteralImpl]"dependents")).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"\nYou have " + [CtVariableReadImpl]prisoners) + [CtLiteralImpl]" prisoner%s", [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]prisoners == [CtLiteralImpl]1 ? [CtLiteralImpl]"" : [CtLiteralImpl]"s")).append([CtInvocationImpl][CtTypeAccessImpl]java.lang.String.format([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"\nYou have " + [CtVariableReadImpl]bondsmen) + [CtLiteralImpl]" %s", [CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]bondsmen == [CtLiteralImpl]1 ? [CtLiteralImpl]"bondsman" : [CtLiteralImpl]"bondsmen"));
        [CtReturnImpl]return [CtInvocationImpl][CtVariableReadImpl]sb.toString();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void doMaintenance([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.unit.Unit u) [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]u.requiresMaintenance()) || [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]campaignOptions.checkMaintenance())) [CtBlockImpl]{
            [CtReturnImpl]return;
        }
        [CtLocalVariableImpl][CtCommentImpl]// lets start by checking times
        [CtTypeReferenceImpl]Person tech = [CtInvocationImpl][CtVariableReadImpl]u.getTech();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int minutesUsed = [CtInvocationImpl][CtVariableReadImpl]u.getMaintenanceTime();
        [CtLocalVariableImpl][CtTypeReferenceImpl]int astechsUsed = [CtInvocationImpl]getAvailableAstechs([CtVariableReadImpl]minutesUsed, [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean maintained = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]tech != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft() >= [CtVariableReadImpl]minutesUsed)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]tech.isMothballing());
        [CtLocalVariableImpl][CtTypeReferenceImpl]boolean paidMaintenance = [CtLiteralImpl]true;
        [CtIfImpl]if ([CtVariableReadImpl]maintained) [CtBlockImpl]{
            [CtInvocationImpl][CtCommentImpl]// use the time
            [CtVariableReadImpl]tech.setMinutesLeft([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]tech.getMinutesLeft() - [CtVariableReadImpl]minutesUsed);
            [CtOperatorAssignmentImpl][CtFieldWriteImpl]astechPoolMinutes -= [CtBinaryOperatorImpl][CtVariableReadImpl]astechsUsed * [CtVariableReadImpl]minutesUsed;
        }
        [CtInvocationImpl][CtVariableReadImpl]u.incrementDaysSinceMaintenance([CtVariableReadImpl]maintained, [CtVariableReadImpl]astechsUsed);
        [CtLocalVariableImpl][CtTypeReferenceImpl]int ruggedMultiplier = [CtLiteralImpl]1;
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasQuirk([CtTypeAccessImpl]OptionsConstants.QUIRK_POS_RUGGED_1)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]ruggedMultiplier = [CtLiteralImpl]2;
        }
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]u.getEntity().hasQuirk([CtTypeAccessImpl]OptionsConstants.QUIRK_POS_RUGGED_2)) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]ruggedMultiplier = [CtLiteralImpl]3;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]u.getDaysSinceMaintenance() >= [CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getMaintenanceCycleDays() * [CtVariableReadImpl]ruggedMultiplier)) [CtBlockImpl]{
            [CtIfImpl][CtCommentImpl]// maybe use the money
            if ([CtInvocationImpl][CtFieldReadImpl]campaignOptions.payForMaintain()) [CtBlockImpl]{
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]finances.debit([CtInvocationImpl][CtVariableReadImpl]u.getMaintenanceCost(), [CtTypeAccessImpl]Transaction.C_MAINTAIN, [CtBinaryOperatorImpl][CtLiteralImpl]"Maintenance for " + [CtInvocationImpl][CtVariableReadImpl]u.getName(), [CtInvocationImpl]getLocalDate())) [CtBlockImpl]{
                    [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='red'><b>You cannot afford to pay maintenance costs for " + [CtInvocationImpl][CtVariableReadImpl]u.getHyperlinkedName()) + [CtLiteralImpl]"!</b></font>");
                    [CtAssignmentImpl][CtVariableWriteImpl]paidMaintenance = [CtLiteralImpl]false;
                }
            }
            [CtLocalVariableImpl][CtCommentImpl]// it is time for a maintenance check
            [CtTypeReferenceImpl]int qualityOrig = [CtInvocationImpl][CtVariableReadImpl]u.getQuality();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String techName = [CtLiteralImpl]"Nobody";
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String techNameLinked = [CtVariableReadImpl]techName;
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtVariableReadImpl]tech) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]techName = [CtInvocationImpl][CtVariableReadImpl]tech.getFullTitle();
                [CtAssignmentImpl][CtVariableWriteImpl]techNameLinked = [CtInvocationImpl][CtVariableReadImpl]tech.getHyperlinkedFullTitle();
            }
            [CtLocalVariableImpl][CtCommentImpl]// don't do actual damage until we clear the for loop to avoid
            [CtCommentImpl]// concurrent mod problems
            [CtCommentImpl]// put it into a hash - 4 points of damage will mean destruction
            [CtTypeReferenceImpl]java.util.Map<[CtTypeReferenceImpl]mekhq.campaign.parts.Part, [CtTypeReferenceImpl]java.lang.Integer> partsToDamage = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.HashMap<>();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.StringBuilder maintenanceReport = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.lang.StringBuilder([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"<emph>" + [CtVariableReadImpl]techName) + [CtLiteralImpl]" performing maintenance</emph><br><br>");
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.parts.Part p : [CtInvocationImpl][CtVariableReadImpl]u.getParts()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partReport = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<b>" + [CtInvocationImpl][CtVariableReadImpl]p.getName()) + [CtLiteralImpl]"</b> (Quality ") + [CtInvocationImpl][CtVariableReadImpl]p.getQualityName()) + [CtLiteralImpl]")";
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]p.needsMaintenance()) [CtBlockImpl]{
                    [CtContinueImpl]continue;
                }
                [CtLocalVariableImpl][CtTypeReferenceImpl]int oldQuality = [CtInvocationImpl][CtVariableReadImpl]p.getQuality();
                [CtLocalVariableImpl][CtTypeReferenceImpl]TargetRoll target = [CtInvocationImpl]getTargetForMaintenance([CtVariableReadImpl]p, [CtVariableReadImpl]tech);
                [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]paidMaintenance) [CtBlockImpl]{
                    [CtInvocationImpl][CtCommentImpl]// TODO : Make this modifier user inputtable
                    [CtVariableReadImpl]target.addModifier([CtLiteralImpl]1, [CtLiteralImpl]"did not pay for maintenance");
                }
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]partReport += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]", TN " + [CtInvocationImpl][CtVariableReadImpl]target.getValue()) + [CtLiteralImpl]"[") + [CtInvocationImpl][CtVariableReadImpl]target.getDesc()) + [CtLiteralImpl]"]";
                [CtLocalVariableImpl][CtTypeReferenceImpl]int roll = [CtInvocationImpl][CtTypeAccessImpl]Compute.d6([CtLiteralImpl]2);
                [CtLocalVariableImpl][CtTypeReferenceImpl]int margin = [CtBinaryOperatorImpl][CtVariableReadImpl]roll - [CtInvocationImpl][CtVariableReadImpl]target.getValue();
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]partReport += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]" rolled a " + [CtVariableReadImpl]roll) + [CtLiteralImpl]", margin of ") + [CtVariableReadImpl]margin;
                [CtSwitchImpl]switch ([CtInvocationImpl][CtVariableReadImpl]p.getQuality()) {
                    [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.parts.Part.QUALITY_A :
                        [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin >= [CtLiteralImpl]4) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]p.improveQuality();
                            }
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]campaignOptions.useUnofficialMaintenance()) [CtBlockImpl]{
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]6)) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]4);
                                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]4)) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]3);
                                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin == [CtUnaryOperatorImpl](-[CtLiteralImpl]4)) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]2);
                                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]1)) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]1);
                                }
                            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]6)) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]1);
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.parts.Part.QUALITY_B :
                        [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin >= [CtLiteralImpl]4) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]p.improveQuality();
                            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]5)) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]p.decreaseQuality();
                            }
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]campaignOptions.useUnofficialMaintenance()) [CtBlockImpl]{
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]6)) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]2);
                                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]2)) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]1);
                                }
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.parts.Part.QUALITY_C :
                        [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]4)) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]p.decreaseQuality();
                            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin >= [CtLiteralImpl]5) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]p.improveQuality();
                            }
                            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]campaignOptions.useUnofficialMaintenance()) [CtBlockImpl]{
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]6)) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]2);
                                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]3)) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]1);
                                }
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.parts.Part.QUALITY_D :
                        [CtBlockImpl]{
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]3)) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]p.decreaseQuality();
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]4)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]campaignOptions.useUnofficialMaintenance())) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]1);
                                }
                            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin >= [CtLiteralImpl]5) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]p.improveQuality();
                            }
                            [CtBreakImpl]break;
                        }
                    [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.parts.Part.QUALITY_E :
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]2)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]p.decreaseQuality();
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]5)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]campaignOptions.useUnofficialMaintenance())) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]1);
                            }
                        } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin >= [CtLiteralImpl]6) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]p.improveQuality();
                        }
                        [CtBreakImpl]break;
                    [CtCaseImpl]case [CtFieldReadImpl]mekhq.campaign.parts.Part.QUALITY_F :
                    [CtCaseImpl]default :
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]2)) [CtBlockImpl]{
                            [CtInvocationImpl][CtVariableReadImpl]p.decreaseQuality();
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]margin < [CtUnaryOperatorImpl](-[CtLiteralImpl]6)) && [CtUnaryOperatorImpl](![CtInvocationImpl][CtFieldReadImpl]campaignOptions.useUnofficialMaintenance())) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]partsToDamage.put([CtVariableReadImpl]p, [CtLiteralImpl]1);
                            }
                        }
                        [CtBreakImpl][CtCommentImpl]// TODO: award XP point if margin >= 6 (make this optional)
                        [CtCommentImpl]// if (margin >= 6) {
                        [CtCommentImpl]// 
                        [CtCommentImpl]// }
                        break;
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getQuality() > [CtVariableReadImpl]oldQuality) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]partReport += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]": <font color='green'>new quality is " + [CtInvocationImpl][CtVariableReadImpl]p.getQualityName()) + [CtLiteralImpl]"</font>";
                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]p.getQuality() < [CtVariableReadImpl]oldQuality) [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]partReport += [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]": <font color='red'>new quality is " + [CtInvocationImpl][CtVariableReadImpl]p.getQualityName()) + [CtLiteralImpl]"</font>";
                } else [CtBlockImpl]{
                    [CtOperatorAssignmentImpl][CtVariableWriteImpl]partReport += [CtBinaryOperatorImpl][CtLiteralImpl]": quality remains " + [CtInvocationImpl][CtVariableReadImpl]p.getQualityName();
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]partsToDamage.get([CtVariableReadImpl]p)) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]partsToDamage.get([CtVariableReadImpl]p) > [CtLiteralImpl]3) [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]partReport += [CtLiteralImpl]", <font color='red'><b>part destroyed</b></font>";
                    } else [CtBlockImpl]{
                        [CtOperatorAssignmentImpl][CtVariableWriteImpl]partReport += [CtLiteralImpl]", <font color='red'><b>part damaged</b></font>";
                    }
                }
                [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]maintenanceReport.append([CtVariableReadImpl]partReport).append([CtLiteralImpl]"<br>");
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int nDamage = [CtLiteralImpl]0;
            [CtLocalVariableImpl][CtTypeReferenceImpl]int nDestroy = [CtLiteralImpl]0;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]mekhq.campaign.parts.Part, [CtTypeReferenceImpl]java.lang.Integer> p : [CtInvocationImpl][CtVariableReadImpl]partsToDamage.entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]int damage = [CtInvocationImpl][CtVariableReadImpl]p.getValue();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]damage > [CtLiteralImpl]3) [CtBlockImpl]{
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]nDestroy++;
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getKey().remove([CtLiteralImpl]false);
                } else [CtBlockImpl]{
                    [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getKey().doMaintenanceDamage([CtVariableReadImpl]damage);
                    [CtUnaryOperatorImpl][CtVariableWriteImpl]nDamage++;
                }
            }
            [CtInvocationImpl][CtVariableReadImpl]u.setLastMaintenanceReport([CtInvocationImpl][CtVariableReadImpl]maintenanceReport.toString());
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().logMaintenance()) [CtBlockImpl]{
                [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MekHQ.getLogger().info([CtInvocationImpl]getClass(), [CtLiteralImpl]"doMaintenance", [CtInvocationImpl][CtVariableReadImpl]maintenanceReport.toString());
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]int quality = [CtInvocationImpl][CtVariableReadImpl]u.getQuality();
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String qualityString;
            [CtLocalVariableImpl][CtTypeReferenceImpl]boolean reverse = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().reverseQualityNames();
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]quality > [CtVariableReadImpl]qualityOrig) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]qualityString = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='green'>Overall quality improves from " + [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.parts.Part.getQualityName([CtVariableReadImpl]qualityOrig, [CtVariableReadImpl]reverse)) + [CtLiteralImpl]" to ") + [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.parts.Part.getQualityName([CtVariableReadImpl]quality, [CtVariableReadImpl]reverse)) + [CtLiteralImpl]"</font>";
            } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]quality < [CtVariableReadImpl]qualityOrig) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]qualityString = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<font color='red'>Overall quality declines from " + [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.parts.Part.getQualityName([CtVariableReadImpl]qualityOrig, [CtVariableReadImpl]reverse)) + [CtLiteralImpl]" to ") + [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.parts.Part.getQualityName([CtVariableReadImpl]quality, [CtVariableReadImpl]reverse)) + [CtLiteralImpl]"</font>";
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]qualityString = [CtBinaryOperatorImpl][CtLiteralImpl]"Overall quality remains " + [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.parts.Part.getQualityName([CtVariableReadImpl]quality, [CtVariableReadImpl]reverse);
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String damageString = [CtLiteralImpl]"";
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nDamage > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]damageString += [CtBinaryOperatorImpl][CtVariableReadImpl]nDamage + [CtLiteralImpl]" parts were damaged. ";
            }
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]nDestroy > [CtLiteralImpl]0) [CtBlockImpl]{
                [CtOperatorAssignmentImpl][CtVariableWriteImpl]damageString += [CtBinaryOperatorImpl][CtVariableReadImpl]nDestroy + [CtLiteralImpl]" parts were destroyed.";
            }
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]damageString.isEmpty()) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]damageString = [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]"<b><font color='red'>" + [CtVariableReadImpl]damageString) + [CtLiteralImpl]"</b></font> [<a href='REPAIR|") + [CtInvocationImpl][CtVariableReadImpl]u.getId()) + [CtLiteralImpl]"'>Repair bay</a>]";
            }
            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String paidString = [CtLiteralImpl]"";
            [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]paidMaintenance) [CtBlockImpl]{
                [CtAssignmentImpl][CtVariableWriteImpl]paidString = [CtLiteralImpl]"<font color='red'>Could not afford maintenance costs, so check is at a penalty.</font>";
            }
            [CtInvocationImpl]addReport([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtVariableReadImpl]techNameLinked + [CtLiteralImpl]" performs maintenance on ") + [CtInvocationImpl][CtVariableReadImpl]u.getHyperlinkedName()) + [CtLiteralImpl]". ") + [CtVariableReadImpl]paidString) + [CtVariableReadImpl]qualityString) + [CtLiteralImpl]". ") + [CtVariableReadImpl]damageString) + [CtLiteralImpl]" [<a href='MAINTENANCE|") + [CtInvocationImpl][CtVariableReadImpl]u.getId()) + [CtLiteralImpl]"'>Get details</a>]");
            [CtInvocationImpl][CtVariableReadImpl]u.resetDaysSinceMaintenance();
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void initTimeInService() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.isDependent()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus().isFree()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate join = [CtLiteralImpl]null;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LogEntry e : [CtInvocationImpl][CtVariableReadImpl]p.getPersonnelLog()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]join == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// If by some nightmare there is no Joined date just use the first entry.
                        [CtVariableWriteImpl]join = [CtInvocationImpl][CtVariableReadImpl]e.getDate();
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDesc().startsWith([CtLiteralImpl]"Joined ") || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDesc().startsWith([CtLiteralImpl]"Freed ")) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]join = [CtInvocationImpl][CtVariableReadImpl]e.getDate();
                        [CtBreakImpl]break;
                    }
                }
                [CtInvocationImpl][CtVariableReadImpl]p.setRecruitment([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]join != [CtLiteralImpl]null ? [CtVariableReadImpl]join : [CtInvocationImpl][CtInvocationImpl]getLocalDate().minusYears([CtLiteralImpl]1));
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void initTimeInRank() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtUnaryOperatorImpl](![CtInvocationImpl][CtVariableReadImpl]p.isDependent()) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getPrisonerStatus().isFree()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate join = [CtLiteralImpl]null;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LogEntry e : [CtInvocationImpl][CtVariableReadImpl]p.getPersonnelLog()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]join == [CtLiteralImpl]null) [CtBlockImpl]{
                        [CtAssignmentImpl][CtCommentImpl]// If by some nightmare there is no date from the below, just use the first entry.
                        [CtVariableWriteImpl]join = [CtInvocationImpl][CtVariableReadImpl]e.getDate();
                    }
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDesc().startsWith([CtLiteralImpl]"Joined ") || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDesc().startsWith([CtLiteralImpl]"Freed ")) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDesc().startsWith([CtLiteralImpl]"Promoted ")) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDesc().startsWith([CtLiteralImpl]"Demoted ")) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]join = [CtInvocationImpl][CtVariableReadImpl]e.getDate();
                    }
                }
                [CtInvocationImpl][CtCommentImpl]// For that one in a billion chance the log is empty. Clone today's date and subtract a year
                [CtVariableReadImpl]p.setLastRankChangeDate([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]join != [CtLiteralImpl]null ? [CtVariableReadImpl]join : [CtInvocationImpl][CtInvocationImpl]getLocalDate().minusYears([CtLiteralImpl]1));
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void initRetirementDateTracking() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person person : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
            [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]person.getStatus().isRetired()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate retired = [CtLiteralImpl]null;
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate lastLoggedDate = [CtLiteralImpl]null;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LogEntry entry : [CtInvocationImpl][CtVariableReadImpl]person.getPersonnelLog()) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]lastLoggedDate = [CtInvocationImpl][CtVariableReadImpl]entry.getDate();
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]entry.getDesc().startsWith([CtLiteralImpl]"Retired")) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]retired = [CtInvocationImpl][CtVariableReadImpl]entry.getDate();
                    }
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtVariableReadImpl]retired == [CtLiteralImpl]null) [CtBlockImpl]{
                    [CtAssignmentImpl][CtVariableWriteImpl]retired = [CtVariableReadImpl]lastLoggedDate;
                }
                [CtInvocationImpl][CtCommentImpl]// For that one in a billion chance the log is empty. Clone today's date and subtract a year
                [CtVariableReadImpl]person.setRetirement([CtConditionalImpl][CtBinaryOperatorImpl][CtVariableReadImpl]retired != [CtLiteralImpl]null ? [CtVariableReadImpl]retired : [CtInvocationImpl][CtInvocationImpl]getLocalDate().minusYears([CtLiteralImpl]1));
            }
        }
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void initAtB([CtParameterImpl][CtTypeReferenceImpl]boolean newCampaign) [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().setLastRetirementRoll([CtInvocationImpl]getLocalDate());
        [CtIfImpl]if ([CtUnaryOperatorImpl]![CtVariableReadImpl]newCampaign) [CtBlockImpl]{
            [CtForEachImpl][CtCommentImpl]/* Switch all contracts to AtBContract's */
            for ([CtLocalVariableImpl][CtTypeReferenceImpl][CtTypeReferenceImpl]java.util.Map.Entry<[CtTypeReferenceImpl]java.lang.Integer, [CtTypeReferenceImpl]mekhq.campaign.mission.Mission> me : [CtInvocationImpl][CtFieldReadImpl]missions.entrySet()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Mission m = [CtInvocationImpl][CtVariableReadImpl]me.getValue();
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]m instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.Contract) && [CtUnaryOperatorImpl](![CtBinaryOperatorImpl]([CtVariableReadImpl]m instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract))) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]me.setValue([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.mission.AtBContract([CtVariableReadImpl](([CtTypeReferenceImpl]mekhq.campaign.mission.Contract) (m)), [CtThisAccessImpl]this));
                }
            }
            [CtLocalVariableImpl][CtCommentImpl]/* Go through all the personnel records and assume the earliest date is the date
            the unit was founded.
             */
            [CtTypeReferenceImpl]java.time.LocalDate founding = [CtLiteralImpl]null;
            [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LogEntry e : [CtInvocationImpl][CtVariableReadImpl]p.getPersonnelLog()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]founding == [CtLiteralImpl]null) || [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDate().isBefore([CtVariableReadImpl]founding)) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]founding = [CtInvocationImpl][CtVariableReadImpl]e.getDate();
                    }
                }
            }
            [CtForEachImpl][CtCommentImpl]/* Go through the personnel records again and assume that any person who joined
            the unit on the founding date is one of the founding members. Also assume
            that MWs assigned to a non-Assault 'Mech on the date they joined came with
            that 'Mech (which is a less certain assumption)
             */
            for ([CtLocalVariableImpl][CtTypeReferenceImpl]Person p : [CtInvocationImpl]getPersonnel()) [CtBlockImpl]{
                [CtLocalVariableImpl][CtTypeReferenceImpl]java.time.LocalDate join = [CtLiteralImpl]null;
                [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LogEntry e : [CtInvocationImpl][CtVariableReadImpl]p.getPersonnelLog()) [CtBlockImpl]{
                    [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDesc().startsWith([CtLiteralImpl]"Joined ")) [CtBlockImpl]{
                        [CtAssignmentImpl][CtVariableWriteImpl]join = [CtInvocationImpl][CtVariableReadImpl]e.getDate();
                        [CtBreakImpl]break;
                    }
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]join != [CtLiteralImpl]null) && [CtInvocationImpl][CtVariableReadImpl]join.equals([CtVariableReadImpl]founding)) [CtBlockImpl]{
                    [CtInvocationImpl][CtVariableReadImpl]p.setFounder([CtLiteralImpl]true);
                }
                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_MECHWARRIOR) || [CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_AERO_PILOT) && [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getAeroRecruitsHaveUnits())) || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.getPrimaryRole() == [CtFieldReadImpl]Person.T_PROTO_PILOT)) [CtBlockImpl]{
                    [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]LogEntry e : [CtInvocationImpl][CtVariableReadImpl]p.getPersonnelLog()) [CtBlockImpl]{
                        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDate().equals([CtVariableReadImpl]join) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDesc().startsWith([CtLiteralImpl]"Assigned to ")) [CtBlockImpl]{
                            [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String mech = [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]e.getDesc().substring([CtLiteralImpl]12);
                            [CtLocalVariableImpl][CtTypeReferenceImpl]MechSummary ms = [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]MechSummaryCache.getInstance().getMech([CtVariableReadImpl]mech);
                            [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtVariableReadImpl]ms) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]p.isFounder() || [CtBinaryOperatorImpl]([CtInvocationImpl][CtVariableReadImpl]ms.getWeightClass() < [CtFieldReadImpl]megamek.common.EntityWeightClass.WEIGHT_ASSAULT))) [CtBlockImpl]{
                                [CtInvocationImpl][CtVariableReadImpl]p.setOriginalUnitWeight([CtInvocationImpl][CtVariableReadImpl]ms.getWeightClass());
                                [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]ms.isClan()) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]p.setOriginalUnitTech([CtTypeAccessImpl]Person.TECH_CLAN);
                                } else [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtVariableReadImpl]ms.getYear() > [CtLiteralImpl]3050) [CtBlockImpl]{
                                    [CtInvocationImpl][CtCommentImpl]// TODO : Fix this so we aren't using a hack that just assumes IS2
                                    [CtVariableReadImpl]p.setOriginalUnitTech([CtTypeAccessImpl]Person.TECH_IS2);
                                }
                                [CtIfImpl]if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtVariableReadImpl]p.getUnitId()) && [CtBinaryOperatorImpl]([CtLiteralImpl]null != [CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtInvocationImpl][CtVariableReadImpl]p.getUnitId()))) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]ms.getName().equals([CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getHangar().getUnit([CtInvocationImpl][CtVariableReadImpl]p.getUnitId()).getEntity().getShortNameRaw())) [CtBlockImpl]{
                                    [CtInvocationImpl][CtVariableReadImpl]p.setOriginalUnitId([CtInvocationImpl][CtVariableReadImpl]p.getUnitId());
                                }
                            }
                        }
                    }
                }
            }
            [CtInvocationImpl]addAllLances([CtFieldReadImpl][CtThisAccessImpl]this.forces);
            [CtInvocationImpl][CtCommentImpl]// Determine whether or not there is an active contract
            setHasActiveContract();
        }
        [CtInvocationImpl]setAtBConfig([CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.againstTheBot.AtBConfiguration.loadFromXml());
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.RandomFactionGenerator.getInstance().startup([CtThisAccessImpl]this);
        [CtInvocationImpl][CtInvocationImpl]getContractMarket().generateContractOffers([CtThisAccessImpl]this, [CtVariableReadImpl]newCampaign);
        [CtInvocationImpl][CtInvocationImpl]getUnitMarket().generateUnitOffers([CtThisAccessImpl]this);
        [CtInvocationImpl]setAtBEventProcessor([CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.module.atb.AtBEventProcessor([CtThisAccessImpl]this));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Stop processing AtB events and release memory.
     */
    public [CtTypeReferenceImpl]void shutdownAtB() [CtBlockImpl]{
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.universe.RandomFactionGenerator.getInstance().dispose();
        [CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]megamek.client.generator.RandomUnitGenerator.getInstance().dispose();
        [CtInvocationImpl][CtFieldReadImpl]atbEventProcessor.shutdown();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean checkOverDueLoans() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money overdueAmount = [CtInvocationImpl][CtInvocationImpl]getFinances().checkOverdueLoanPayments([CtThisAccessImpl]this);
        [CtIfImpl]if ([CtInvocationImpl][CtVariableReadImpl]overdueAmount.isPositive()) [CtBlockImpl]{
            [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showMessageDialog([CtLiteralImpl]null, [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]"You have overdue loan payments totaling " + [CtInvocationImpl][CtVariableReadImpl]overdueAmount.toAmountAndSymbolString()) + [CtLiteralImpl]"\nYou must deal with these payments before advancing the day.\nHere are some options:\n  - Sell off equipment to generate funds.\n  - Pay off the collateral on the loan.\n  - Default on the loan.\n  - Just cheat and remove the loan via GM mode.", [CtLiteralImpl]"Overdue Loan Payments", [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]WARNING_MESSAGE);
            [CtReturnImpl]return [CtLiteralImpl]true;
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean checkRetirementDefections() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().getRetirees().size() > [CtLiteralImpl]0) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] options = [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"Show Payout Dialog", [CtLiteralImpl]"Cancel" };
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_OPTION == [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtLiteralImpl]null, [CtLiteralImpl]"You have personnel who have left the unit or been killed in action but have not received their final payout.\nYou must deal with these payments before advancing the day.\nHere are some options:\n  - Sell off equipment to generate funds.\n  - Pay one or more personnel in equipment.\n  - Just cheat and use GM mode to edit the settlement.", [CtLiteralImpl]"Unresolved Final Payments", [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]OK_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]WARNING_MESSAGE, [CtLiteralImpl]null, [CtVariableReadImpl]options, [CtArrayReadImpl][CtVariableReadImpl]options[[CtLiteralImpl]0]);
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]boolean checkYearlyRetirements() [CtBlockImpl]{
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUseAtB() && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl][CtTypeAccessImpl]java.time.temporal.ChronoUnit.[CtFieldReferenceImpl]DAYS.between([CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().getLastRetirementRoll(), [CtInvocationImpl]getLocalDate()) == [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]getRetirementDefectionTracker().getLastRetirementRoll().lengthOfYear())) [CtBlockImpl]{
            [CtLocalVariableImpl][CtArrayTypeReferenceImpl]java.lang.Object[] options = [CtNewArrayImpl]new java.lang.Object[]{ [CtLiteralImpl]"Show Retirement Dialog", [CtLiteralImpl]"Not Now" };
            [CtReturnImpl]return [CtBinaryOperatorImpl][CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]YES_OPTION == [CtInvocationImpl][CtTypeAccessImpl]javax.swing.JOptionPane.showOptionDialog([CtLiteralImpl]null, [CtLiteralImpl]"It has been a year since the last retirement/defection roll, and it is time to do another.", [CtLiteralImpl]"Retirement/Defection roll required", [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]OK_CANCEL_OPTION, [CtFieldReadImpl][CtTypeAccessImpl]javax.swing.JOptionPane.[CtFieldReferenceImpl]WARNING_MESSAGE, [CtLiteralImpl]null, [CtVariableReadImpl]options, [CtArrayReadImpl][CtVariableReadImpl]options[[CtLiteralImpl]0]);
        }
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the type of rating method used.
     */
    public [CtTypeReferenceImpl]void setUnitRating([CtParameterImpl][CtTypeReferenceImpl]mekhq.campaign.rating.IUnitRating rating) [CtBlockImpl]{
        [CtAssignmentImpl][CtFieldWriteImpl]unitRating = [CtVariableReadImpl]rating;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Returns the type of rating method as selected in the Campaign Options dialog.
     * Lazy-loaded for performance. Default is CampaignOpsReputation
     */
    public [CtTypeReferenceImpl]mekhq.campaign.rating.IUnitRating getUnitRating() [CtBlockImpl]{
        [CtIfImpl][CtCommentImpl]// if we switched unit rating methods,
        if ([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtFieldReadImpl]unitRating != [CtLiteralImpl]null) && [CtBinaryOperatorImpl]([CtInvocationImpl][CtFieldReadImpl]unitRating.getUnitRatingMethod() != [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUnitRatingMethod())) [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]unitRating = [CtLiteralImpl]null;
        }
        [CtIfImpl]if ([CtBinaryOperatorImpl][CtFieldReadImpl]unitRating == [CtLiteralImpl]null) [CtBlockImpl]{
            [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.rating.UnitRatingMethod method = [CtInvocationImpl][CtInvocationImpl]getCampaignOptions().getUnitRatingMethod();
            [CtIfImpl]if ([CtInvocationImpl][CtTypeAccessImpl]UnitRatingMethod.FLD_MAN_MERCS_REV.equals([CtVariableReadImpl]method)) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]unitRating = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.rating.FieldManualMercRevDragoonsRating([CtThisAccessImpl]this);
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]unitRating = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.rating.CampaignOpsReputation([CtThisAccessImpl]this);
            }
        }
        [CtReturnImpl]return [CtFieldReadImpl]unitRating;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets peacetime costs including salaries.
     *
     * @return The peacetime costs of the campaign including salaries.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Money getPeacetimeCost() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getPeacetimeCost([CtLiteralImpl]true);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets peacetime costs, optionally including salaries.
     *
     * This can be used to ensure salaries are not double counted.
     *
     * @param includeSalaries
     * 		A value indicating whether or not salaries
     * 		should be included in peacetime cost calculations.
     * @return The peacetime costs of the campaign, optionally including salaries.
     */
    public [CtTypeReferenceImpl]mekhq.campaign.Money getPeacetimeCost([CtParameterImpl][CtTypeReferenceImpl]boolean includeSalaries) [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Money peaceTimeCosts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]Money.zero().plus([CtInvocationImpl]getMonthlySpareParts()).plus([CtInvocationImpl]getMonthlyFuel()).plus([CtInvocationImpl]getMonthlyAmmo());
        [CtIfImpl]if ([CtVariableReadImpl]includeSalaries) [CtBlockImpl]{
            [CtAssignmentImpl][CtVariableWriteImpl]peaceTimeCosts = [CtInvocationImpl][CtVariableReadImpl]peaceTimeCosts.plus([CtInvocationImpl]getPayRoll([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().useInfantryDontCount()));
        }
        [CtReturnImpl]return [CtVariableReadImpl]peaceTimeCosts;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getMonthlySpareParts() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getHangar().getUnitCosts([CtLambdaImpl]([CtParameterImpl] u) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isMothballed(), [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getSparePartsCost);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getMonthlyFuel() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getHangar().getUnitCosts([CtLambdaImpl]([CtParameterImpl] u) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isMothballed(), [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getFuelCost);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]mekhq.campaign.Money getMonthlyAmmo() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getHangar().getUnitCosts([CtLambdaImpl]([CtParameterImpl] u) -> [CtUnaryOperatorImpl]![CtInvocationImpl][CtVariableReadImpl]u.isMothballed(), [CtExecutableReferenceExpressionImpl][CtFieldReadImpl]Unit::getAmmoCost);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getTechIntroYear() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getCampaignOptions().limitByYear()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl]getGameYear();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtFieldReadImpl][CtTypeAccessImpl]java.lang.Integer.[CtFieldReferenceImpl]MAX_VALUE;
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getGameYear() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getLocalDate().getYear();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]int getTechFaction() [CtBlockImpl]{
        [CtReturnImpl]return [CtFieldReadImpl]techFactionCode;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void updateTechFactionCode() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl][CtFieldReadImpl]campaignOptions.useFactionIntroDate()) [CtBlockImpl]{
            [CtForImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]int i = [CtLiteralImpl]0; [CtBinaryOperatorImpl][CtVariableReadImpl]i < [CtFieldReadImpl]ITechnology.MM_FACTION_CODES.length; [CtUnaryOperatorImpl][CtVariableWriteImpl]i++) [CtBlockImpl]{
                [CtIfImpl]if ([CtInvocationImpl][CtArrayReadImpl][CtFieldReadImpl]ITechnology.MM_FACTION_CODES[[CtVariableReadImpl]i].equals([CtFieldReadImpl]factionCode)) [CtBlockImpl]{
                    [CtAssignmentImpl][CtFieldWriteImpl]techFactionCode = [CtVariableReadImpl]i;
                    [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTechProgression.loadFaction([CtFieldReadImpl]techFactionCode);
                    [CtReturnImpl]return;
                }
            }
            [CtIfImpl][CtCommentImpl]// If the tech progression data does not include the current faction,
            [CtCommentImpl]// use a generic.
            if ([CtInvocationImpl][CtInvocationImpl]getFaction().isClan()) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]techFactionCode = [CtFieldReadImpl]ITechnology.F_CLAN;
            } else [CtIfImpl]if ([CtInvocationImpl][CtInvocationImpl]getFaction().isPeriphery()) [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]techFactionCode = [CtFieldReadImpl]ITechnology.F_PER;
            } else [CtBlockImpl]{
                [CtAssignmentImpl][CtFieldWriteImpl]techFactionCode = [CtFieldReadImpl]ITechnology.F_IS;
            }
        } else [CtBlockImpl]{
            [CtAssignmentImpl][CtFieldWriteImpl]techFactionCode = [CtFieldReadImpl]ITechnology.F_NONE;
        }
        [CtInvocationImpl][CtCommentImpl]// Unit tech level will be calculated if the code has changed.
        [CtTypeAccessImpl]mekhq.campaign.unit.UnitTechProgression.loadFaction([CtFieldReadImpl]techFactionCode);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean useClanTechBase() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtInvocationImpl]getFaction().isClan();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean useMixedTech() [CtBlockImpl]{
        [CtIfImpl]if ([CtInvocationImpl]useClanTechBase()) [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]campaignOptions.allowISPurchases();
        } else [CtBlockImpl]{
            [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]campaignOptions.allowClanPurchases();
        }
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]mekhq.campaign.SimpleTechLevel getTechLevel() [CtBlockImpl]{
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]SimpleTechLevel lvl : [CtInvocationImpl][CtTypeAccessImpl]SimpleTechLevel.values()) [CtBlockImpl]{
            [CtIfImpl]if ([CtBinaryOperatorImpl][CtInvocationImpl][CtFieldReadImpl]campaignOptions.getTechLevel() == [CtInvocationImpl][CtVariableReadImpl]lvl.ordinal()) [CtBlockImpl]{
                [CtReturnImpl]return [CtVariableReadImpl]lvl;
            }
        }
        [CtReturnImpl]return [CtFieldReadImpl]SimpleTechLevel.UNOFFICIAL;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean unofficialNoYear() [CtBlockImpl]{
        [CtReturnImpl]return [CtLiteralImpl]false;
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean useVariableTechLevel() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl][CtFieldReadImpl]campaignOptions.useVariableTechLevel();
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Override
    public [CtTypeReferenceImpl]boolean showExtinct() [CtBlockImpl]{
        [CtReturnImpl]return [CtUnaryOperatorImpl]![CtInvocationImpl][CtFieldReadImpl]campaignOptions.disallowExtinctStuff();
    }
}