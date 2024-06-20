[CompilationUnitImpl][CtCommentImpl]/* Copyright (C) 2020 MegaMek team

This file is part of MekHQ.

MekHQ is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

MekHQ is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with MekHQ.  If not, see <http://www.gnu.org/licenses/>.
 */
[CtPackageDeclarationImpl]package mekhq.campaign.parts;
[CtUnresolvedImport]import mekhq.campaign.unit.Unit;
[CtImportImpl]import java.util.Set;
[CtUnresolvedImport]import mekhq.Version;
[CtImportImpl]import org.w3c.dom.Element;
[CtImportImpl]import java.util.ArrayList;
[CtImportImpl]import org.w3c.dom.Document;
[CtImportImpl]import org.xml.sax.SAXException;
[CtUnresolvedImport]import megamek.common.EquipmentType;
[CtImportImpl]import java.io.StringWriter;
[CtUnresolvedImport]import mekhq.campaign.CampaignOptions;
[CtImportImpl]import javax.xml.parsers.ParserConfigurationException;
[CtImportImpl]import java.util.List;
[CtImportImpl]import java.util.UUID;
[CtUnresolvedImport]import mekhq.campaign.Warehouse;
[CtImportImpl]import java.io.PrintWriter;
[CtImportImpl]import java.util.stream.Collectors;
[CtUnresolvedImport]import megamek.common.IPlayer;
[CtUnresolvedImport]import mekhq.MekHqXmlUtil;
[CtUnresolvedImport]import mekhq.campaign.finances.Money;
[CtUnresolvedImport]import mekhq.campaign.parts.equipment.MissingEquipmentPart;
[CtUnresolvedImport]import static org.junit.Assert.*;
[CtImportImpl]import java.io.IOException;
[CtUnresolvedImport]import org.junit.Test;
[CtUnresolvedImport]import static org.mockito.Mockito.*;
[CtUnresolvedImport]import mekhq.campaign.unit.UnitTestUtilities;
[CtUnresolvedImport]import mekhq.campaign.personnel.Person;
[CtImportImpl]import javax.xml.parsers.DocumentBuilder;
[CtUnresolvedImport]import mekhq.campaign.Campaign;
[CtImportImpl]import java.io.ByteArrayInputStream;
[CtUnresolvedImport]import mekhq.campaign.parts.equipment.EquipmentPart;
[CtImportImpl]import java.util.Arrays;
[CtUnresolvedImport]import megamek.common.Entity;
[CtUnresolvedImport]import mekhq.campaign.parts.equipment.AmmoBin;
[CtClassImpl]public class RefitTest {
    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void deserializationCtor() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]Refit refit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Refit();
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]refit);
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void newRefitCtor() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Campaign mockCampaign = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Campaign.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Warehouse mockWarehouse = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Warehouse.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getWarehouse()).thenReturn([CtVariableReadImpl]mockWarehouse);
        [CtLocalVariableImpl][CtCommentImpl]// Create the original entity backing the unit
        [CtTypeReferenceImpl]megamek.common.Entity oldEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getLocustLCT1V();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.IPlayer mockPlayer = [CtInvocationImpl]mock([CtFieldReadImpl]megamek.common.IPlayer.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockPlayer.getName()).thenReturn([CtLiteralImpl]"Test Player");
        [CtInvocationImpl][CtVariableReadImpl]oldEntity.setOwner([CtVariableReadImpl]mockPlayer);
        [CtLocalVariableImpl][CtCommentImpl]// Create the entity we're going to refit to
        [CtTypeReferenceImpl]megamek.common.Entity newEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getLocustLCT1E();
        [CtLocalVariableImpl][CtCommentImpl]// Create the unit which will be refit
        [CtTypeReferenceImpl]mekhq.campaign.unit.Unit oldUnit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtVariableReadImpl]oldEntity, [CtVariableReadImpl]mockCampaign);
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.initializeParts([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Create the Refit
        [CtTypeReferenceImpl]Refit refit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Refit([CtVariableReadImpl]oldUnit, [CtVariableReadImpl]newEntity, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]mockCampaign, [CtInvocationImpl][CtVariableReadImpl]refit.getCampaign());
        [CtInvocationImpl][CtCommentImpl]// Should be old parts...
        assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getOldUnitParts().isEmpty());
        [CtInvocationImpl][CtCommentImpl]// ...and new parts.
        assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getNewUnitParts().isEmpty());
        [CtInvocationImpl][CtCommentImpl]// ...and we'll need to buy some parts
        assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getShoppingList().isEmpty());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void locust1Vto1ETest() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Campaign mockCampaign = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Campaign.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Warehouse mockWarehouse = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Warehouse.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getWarehouse()).thenReturn([CtVariableReadImpl]mockWarehouse);
        [CtLocalVariableImpl][CtCommentImpl]// Create the original entity backing the unit
        [CtTypeReferenceImpl]megamek.common.Entity oldEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getLocustLCT1V();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.IPlayer mockPlayer = [CtInvocationImpl]mock([CtFieldReadImpl]megamek.common.IPlayer.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockPlayer.getName()).thenReturn([CtLiteralImpl]"Test Player");
        [CtInvocationImpl][CtVariableReadImpl]oldEntity.setOwner([CtVariableReadImpl]mockPlayer);
        [CtLocalVariableImpl][CtCommentImpl]// Create the entity we're going to refit to
        [CtTypeReferenceImpl]megamek.common.Entity newEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getLocustLCT1E();
        [CtLocalVariableImpl][CtCommentImpl]// Create the unit which will be refit
        [CtTypeReferenceImpl]mekhq.campaign.unit.Unit oldUnit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtVariableReadImpl]oldEntity, [CtVariableReadImpl]mockCampaign);
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.setId([CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID());
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.initializeParts([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Create the Refit
        [CtTypeReferenceImpl]Refit refit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Refit([CtVariableReadImpl]oldUnit, [CtVariableReadImpl]newEntity, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]mockCampaign, [CtInvocationImpl][CtVariableReadImpl]refit.getCampaign());
        [CtInvocationImpl][CtCommentImpl]// 
        [CtCommentImpl]// Locust 1V to 1E Class D refit steps (in no particular order):
        [CtCommentImpl]// 1. Remove excess Machine Gun (LA) [120 mins]
        [CtCommentImpl]// 2. Remove excess Machine Gun (RA) [120 mins]
        [CtCommentImpl]// 3. Remove Machine Gun Ammo Bin (CT) [120 mins]
        [CtCommentImpl]// 4. Move Medium Laser (CT) to (RA) [120 mins]
        [CtCommentImpl]// 5. Add Medium Laser to (LA) [120 mins]
        [CtCommentImpl]// 6. Add Small Laser to (RA) [120 mins]
        [CtCommentImpl]// 7. Add Small Laser to (LA) [120 mins]
        [CtCommentImpl]// 
        [CtCommentImpl]// Everything else is the same.
        [CtCommentImpl]// 
        [CtCommentImpl]// Per SO p188:
        [CtCommentImpl]// "This kit permits players to install a new item
        [CtCommentImpl]// where previously there was none..."
        assertEquals([CtTypeAccessImpl]Refit.CLASS_D, [CtInvocationImpl][CtVariableReadImpl]refit.getRefitClass());
        [CtInvocationImpl][CtCommentImpl]// Time?
        [CtCommentImpl]// + 3 removals @ 120 mins ea
        [CtCommentImpl]// + 1 move @ 120 mins ea
        [CtCommentImpl]// + 3 adds @ 120 mins ea
        [CtCommentImpl]// x 3 (Class D)
        assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]120.0 * [CtLiteralImpl]7.0) * [CtLiteralImpl]3.0, [CtInvocationImpl][CtVariableReadImpl]refit.getActualTime(), [CtLiteralImpl]0.1);
        [CtInvocationImpl][CtCommentImpl]// Cost?
        [CtCommentImpl]// + 1 Medium Laser @ 40,000 ea
        [CtCommentImpl]// + 2 Small Lasers @ 11,250 ea
        [CtCommentImpl]// x 1.1 (Refit Kit cost, SO p188)
        assertEquals([CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.finances.Money.of([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]40000 + [CtLiteralImpl]11250) + [CtLiteralImpl]11250) * [CtLiteralImpl]1.1), [CtInvocationImpl][CtVariableReadImpl]refit.getCost());
        [CtLocalVariableImpl][CtCommentImpl]// We're removing 2 machine guns and an ammo bin
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Part> removedParts = [CtInvocationImpl][CtVariableReadImpl]refit.getOldUnitParts();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]3, [CtInvocationImpl][CtVariableReadImpl]removedParts.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]removedParts.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"Machine Gun")).count());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]removedParts.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.AmmoBin) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"Machine Gun Ammo Bin")).count());
        [CtLocalVariableImpl][CtCommentImpl]// All of the new parts should be from the old unit
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Part> newParts = [CtInvocationImpl][CtVariableReadImpl]refit.getNewUnitParts();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]newParts.stream().allMatch([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getUnit().equals([CtVariableReadImpl]oldUnit)));
        [CtLocalVariableImpl][CtCommentImpl]// We need to buy one Medium Laser and two Small Lasers
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Part> shoppingCart = [CtInvocationImpl][CtVariableReadImpl]refit.getShoppingList();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]shoppingCart.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"Medium Laser")).count());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]shoppingCart.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"Small Laser")).count());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testLocust1Vto1EWriteToXml() throws [CtTypeReferenceImpl]javax.xml.parsers.ParserConfigurationException, [CtTypeReferenceImpl]org.xml.sax.SAXException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Campaign mockCampaign = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Campaign.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getEntities()).thenReturn([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Warehouse mockWarehouse = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Warehouse.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getWarehouse()).thenReturn([CtVariableReadImpl]mockWarehouse);
        [CtLocalVariableImpl][CtCommentImpl]// Create the original entity backing the unit
        [CtTypeReferenceImpl]megamek.common.Entity oldEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getLocustLCT1V();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.IPlayer mockPlayer = [CtInvocationImpl]mock([CtFieldReadImpl]megamek.common.IPlayer.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockPlayer.getName()).thenReturn([CtLiteralImpl]"Test Player");
        [CtInvocationImpl][CtVariableReadImpl]oldEntity.setOwner([CtVariableReadImpl]mockPlayer);
        [CtLocalVariableImpl][CtCommentImpl]// Create the entity we're going to refit to
        [CtTypeReferenceImpl]megamek.common.Entity newEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getLocustLCT1E();
        [CtLocalVariableImpl][CtCommentImpl]// Create the unit which will be refit
        [CtTypeReferenceImpl]mekhq.campaign.unit.Unit oldUnit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtVariableReadImpl]oldEntity, [CtVariableReadImpl]mockCampaign);
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.setId([CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID());
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.initializeParts([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Make sure the unit parts have an ID before we serialize them
        [CtTypeReferenceImpl]int partId = [CtLiteralImpl]1;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Part part : [CtInvocationImpl][CtVariableReadImpl]oldUnit.getParts()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]part.setId([CtUnaryOperatorImpl][CtVariableWriteImpl]partId++);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Create the Refit
        [CtTypeReferenceImpl]Refit refit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Refit([CtVariableReadImpl]oldUnit, [CtVariableReadImpl]newEntity, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Write the Refit XML
        [CtTypeReferenceImpl]java.io.StringWriter sw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintWriter pw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]sw);
        [CtInvocationImpl][CtVariableReadImpl]refit.writeToXml([CtVariableReadImpl]pw, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtCommentImpl]// Get the Refit XML
        [CtTypeReferenceImpl]java.lang.String xml = [CtInvocationImpl][CtVariableReadImpl]sw.toString();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]xml.trim().isEmpty());
        [CtLocalVariableImpl][CtCommentImpl]// Using factory get an instance of document builder
        [CtTypeReferenceImpl]javax.xml.parsers.DocumentBuilder db = [CtInvocationImpl][CtTypeAccessImpl]mekhq.MekHqXmlUtil.newSafeDocumentBuilder();
        [CtLocalVariableImpl][CtCommentImpl]// Parse using builder to get DOM representation of the XML file
        [CtTypeReferenceImpl]org.w3c.dom.Document xmlDoc = [CtInvocationImpl][CtVariableReadImpl]db.parse([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayInputStream([CtInvocationImpl][CtVariableReadImpl]xml.getBytes()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Element refitElt = [CtInvocationImpl][CtVariableReadImpl]xmlDoc.getDocumentElement();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"refit", [CtInvocationImpl][CtVariableReadImpl]refitElt.getNodeName());
        [CtLocalVariableImpl][CtCommentImpl]// Deserialize the refit
        [CtTypeReferenceImpl]Refit deserialized = [CtInvocationImpl][CtTypeAccessImpl]Refit.generateInstanceFromXML([CtVariableReadImpl]refitElt, [CtVariableReadImpl]oldUnit, [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.Version([CtLiteralImpl]"1.0.0"));
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]deserialized);
        [CtInvocationImpl][CtCommentImpl]// Spot check the values
        assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTime(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTime());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getActualTime(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getActualTime());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getCost(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getCost());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.isSameArmorType(), [CtInvocationImpl][CtVariableReadImpl]deserialized.isSameArmorType());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.hasFailedCheck(), [CtInvocationImpl][CtVariableReadImpl]deserialized.hasFailedCheck());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getRefitClass(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getRefitClass());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTimeSpent(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTimeSpent());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTimeLeft(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTimeLeft());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.isCustomJob(), [CtInvocationImpl][CtVariableReadImpl]deserialized.isCustomJob());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.kitFound(), [CtInvocationImpl][CtVariableReadImpl]deserialized.kitFound());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.isBeingRefurbished(), [CtInvocationImpl][CtVariableReadImpl]deserialized.isBeingRefurbished());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTech(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTech());
        [CtLocalVariableImpl][CtCommentImpl]// Check that we got all the correct old parts in the XML
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> oldUnitParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getOldUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> serializedOldParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getOldUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]oldUnitParts, [CtVariableReadImpl]serializedOldParts);
        [CtLocalVariableImpl][CtCommentImpl]// Check that we got all the correct new parts in the XML
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> newUnitParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getNewUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> serializedNewParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getNewUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]newUnitParts, [CtVariableReadImpl]serializedNewParts);
        [CtLocalVariableImpl][CtCommentImpl]// Check that we got all the shopping list entries (by name, not amazing but reasonable)
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> shoppingList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getShoppingList().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getName()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> serializedShoppingList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getShoppingList().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getName()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtInvocationImpl][CtCommentImpl]// Make sure they're the same length first...
        assertEquals([CtInvocationImpl][CtVariableReadImpl]shoppingList.size(), [CtInvocationImpl][CtVariableReadImpl]serializedShoppingList.size());
        [CtForEachImpl][CtCommentImpl]// ...then make sure they're the "same" by removing them one by one...
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partName : [CtVariableReadImpl]shoppingList) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingList.remove([CtVariableReadImpl]partName));
        }
        [CtInvocationImpl][CtCommentImpl]// ...and ensuring nothing is left.
        assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingList.isEmpty());
        [CtLocalVariableImpl][CtCommentImpl]// Do the same for their descriptions, which include the quantities...
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> shoppingListDescs = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]refit.getShoppingListDescription());
        [CtLocalVariableImpl][CtCommentImpl]// ...except the second list needs to be mutable.
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> serializedShoppingListDescs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]deserialized.getShoppingListDescription()));
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]shoppingListDescs.size(), [CtInvocationImpl][CtVariableReadImpl]serializedShoppingListDescs.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String desc : [CtVariableReadImpl]shoppingListDescs) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingListDescs.remove([CtVariableReadImpl]desc));
        }
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingListDescs.isEmpty());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void javelinJVN10Nto10ATest() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Campaign mockCampaign = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Campaign.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.CampaignOptions mockCampaignOptions = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.CampaignOptions.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getCampaignOptions()).thenReturn([CtVariableReadImpl]mockCampaignOptions);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Warehouse mockWarehouse = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Warehouse.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getWarehouse()).thenReturn([CtVariableReadImpl]mockWarehouse);
        [CtLocalVariableImpl][CtCommentImpl]// Create the original entity backing the unit
        [CtTypeReferenceImpl]megamek.common.Entity oldEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getJavelinJVN10N();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.IPlayer mockPlayer = [CtInvocationImpl]mock([CtFieldReadImpl]megamek.common.IPlayer.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockPlayer.getName()).thenReturn([CtLiteralImpl]"Test Player");
        [CtInvocationImpl][CtVariableReadImpl]oldEntity.setOwner([CtVariableReadImpl]mockPlayer);
        [CtLocalVariableImpl][CtCommentImpl]// Create the entity we're going to refit to
        [CtTypeReferenceImpl]megamek.common.Entity newEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getJavelinJVN10A();
        [CtLocalVariableImpl][CtCommentImpl]// Create the unit which will be refit
        [CtTypeReferenceImpl]mekhq.campaign.unit.Unit oldUnit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtVariableReadImpl]oldEntity, [CtVariableReadImpl]mockCampaign);
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.setId([CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID());
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.initializeParts([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Create the Refit
        [CtTypeReferenceImpl]Refit refit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Refit([CtVariableReadImpl]oldUnit, [CtVariableReadImpl]newEntity, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]mockCampaign, [CtInvocationImpl][CtVariableReadImpl]refit.getCampaign());
        [CtInvocationImpl][CtCommentImpl]// 
        [CtCommentImpl]// Javelin 10N to 10A Class C refit steps (in no particular order):
        [CtCommentImpl]// 1. Remove excess SRM 6 (LT) [120 mins]
        [CtCommentImpl]// 2. Remove excess SRM 6 (RT) [120 mins]
        [CtCommentImpl]// 3. Remove SRM 6 Ammo Bin (LT) [120 mins]
        [CtCommentImpl]// 4. Remove SRM 6 Ammo Bin (RT) [120 mins]
        [CtCommentImpl]// 5. Add LRM 15 to (RT) [120 mins]
        [CtCommentImpl]// 6. Add LRM 15 Ammo Bin to (RT) [120 mins]
        [CtCommentImpl]// 
        [CtCommentImpl]// Everything else is the same.
        [CtCommentImpl]// 
        [CtCommentImpl]// Per SO p188:
        [CtCommentImpl]// "A Class C kit also enables replacement of a weapon
        [CtCommentImpl]// or item of equipment with any other, even if it is
        [CtCommentImpl]// larger than the item(s) being replaced; for example,
        [CtCommentImpl]// replacing an ER large laser with an LRM-10 launcher
        [CtCommentImpl]// and ammunition."
        assertEquals([CtTypeAccessImpl]Refit.CLASS_C, [CtInvocationImpl][CtVariableReadImpl]refit.getRefitClass());
        [CtInvocationImpl][CtCommentImpl]// Time?
        [CtCommentImpl]// + 4 removals @ 120 mins ea
        [CtCommentImpl]// + 2 adds @ 120 mins ea
        [CtCommentImpl]// x 2 (Class C)
        assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtLiteralImpl]120.0 * [CtLiteralImpl]6.0) * [CtLiteralImpl]2.0, [CtInvocationImpl][CtVariableReadImpl]refit.getActualTime(), [CtLiteralImpl]0.1);
        [CtInvocationImpl][CtCommentImpl]// Cost?
        [CtCommentImpl]// + 1 LRM 15 @ 175,000 ea
        [CtCommentImpl]// + 1 ton LRM 15 Ammo @ 30,000 ea
        [CtCommentImpl]// x 1.1 (Refit Kit cost, SO p188)
        assertEquals([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.finances.Money.of([CtBinaryOperatorImpl][CtLiteralImpl]175000.0 + [CtLiteralImpl]30000.0).multipliedBy([CtLiteralImpl]1.1), [CtInvocationImpl][CtVariableReadImpl]refit.getCost());
        [CtLocalVariableImpl][CtCommentImpl]// We're removing 2 SRM 6s and two ammo bins
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Part> removedParts = [CtInvocationImpl][CtVariableReadImpl]refit.getOldUnitParts();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]4, [CtInvocationImpl][CtVariableReadImpl]removedParts.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]removedParts.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"SRM 6")).count());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]removedParts.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.AmmoBin) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"SRM 6 Ammo Bin")).count());
        [CtLocalVariableImpl][CtCommentImpl]// All of the new parts should be from the old unit
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Part> newParts = [CtInvocationImpl][CtVariableReadImpl]refit.getNewUnitParts();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]newParts.stream().allMatch([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getUnit().equals([CtVariableReadImpl]oldUnit)));
        [CtLocalVariableImpl][CtCommentImpl]// We need to buy one LRM 15 and one LRM 15 Ammo Bin
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Part> shoppingCart = [CtInvocationImpl][CtVariableReadImpl]refit.getShoppingList();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]shoppingCart.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"LRM 15")).count());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]shoppingCart.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.AmmoBin) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"LRM 15 Ammo Bin")).count());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testJavelinJVN10Nto10AWriteToXml() throws [CtTypeReferenceImpl]javax.xml.parsers.ParserConfigurationException, [CtTypeReferenceImpl]org.xml.sax.SAXException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Campaign mockCampaign = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Campaign.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getEntities()).thenReturn([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.CampaignOptions mockCampaignOptions = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.CampaignOptions.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getCampaignOptions()).thenReturn([CtVariableReadImpl]mockCampaignOptions);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Warehouse mockWarehouse = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Warehouse.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getWarehouse()).thenReturn([CtVariableReadImpl]mockWarehouse);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.personnel.Person mockTech = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.personnel.Person.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID techId = [CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID();
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockTech.getId()).thenReturn([CtVariableReadImpl]techId);
        [CtLocalVariableImpl][CtCommentImpl]// Create the original entity backing the unit
        [CtTypeReferenceImpl]megamek.common.Entity oldEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getJavelinJVN10N();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.IPlayer mockPlayer = [CtInvocationImpl]mock([CtFieldReadImpl]megamek.common.IPlayer.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockPlayer.getName()).thenReturn([CtLiteralImpl]"Test Player");
        [CtInvocationImpl][CtVariableReadImpl]oldEntity.setOwner([CtVariableReadImpl]mockPlayer);
        [CtLocalVariableImpl][CtCommentImpl]// Create the entity we're going to refit to
        [CtTypeReferenceImpl]megamek.common.Entity newEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getJavelinJVN10A();
        [CtLocalVariableImpl][CtCommentImpl]// Create the unit which will be refit
        [CtTypeReferenceImpl]mekhq.campaign.unit.Unit oldUnit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtVariableReadImpl]oldEntity, [CtVariableReadImpl]mockCampaign);
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.setId([CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID());
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.initializeParts([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Make sure the unit parts have an ID before we serialize them
        [CtTypeReferenceImpl]int partId = [CtLiteralImpl]1;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Part part : [CtInvocationImpl][CtVariableReadImpl]oldUnit.getParts()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]part.setId([CtUnaryOperatorImpl][CtVariableWriteImpl]partId++);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Create the Refit
        [CtTypeReferenceImpl]Refit refit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Refit([CtVariableReadImpl]oldUnit, [CtVariableReadImpl]newEntity, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]refit.setTech([CtVariableReadImpl]mockTech);
        [CtInvocationImpl][CtVariableReadImpl]refit.addTimeSpent([CtLiteralImpl]60);[CtCommentImpl]// 1 hour of work!

        [CtLocalVariableImpl][CtCommentImpl]// Write the Refit XML
        [CtTypeReferenceImpl]java.io.StringWriter sw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintWriter pw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]sw);
        [CtInvocationImpl][CtVariableReadImpl]refit.writeToXml([CtVariableReadImpl]pw, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtCommentImpl]// Get the Refit XML
        [CtTypeReferenceImpl]java.lang.String xml = [CtInvocationImpl][CtVariableReadImpl]sw.toString();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]xml.trim().isEmpty());
        [CtLocalVariableImpl][CtCommentImpl]// Using factory get an instance of document builder
        [CtTypeReferenceImpl]javax.xml.parsers.DocumentBuilder db = [CtInvocationImpl][CtTypeAccessImpl]mekhq.MekHqXmlUtil.newSafeDocumentBuilder();
        [CtLocalVariableImpl][CtCommentImpl]// Parse using builder to get DOM representation of the XML file
        [CtTypeReferenceImpl]org.w3c.dom.Document xmlDoc = [CtInvocationImpl][CtVariableReadImpl]db.parse([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayInputStream([CtInvocationImpl][CtVariableReadImpl]xml.getBytes()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Element refitElt = [CtInvocationImpl][CtVariableReadImpl]xmlDoc.getDocumentElement();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"refit", [CtInvocationImpl][CtVariableReadImpl]refitElt.getNodeName());
        [CtLocalVariableImpl][CtCommentImpl]// Deserialize the refit
        [CtTypeReferenceImpl]Refit deserialized = [CtInvocationImpl][CtTypeAccessImpl]Refit.generateInstanceFromXML([CtVariableReadImpl]refitElt, [CtVariableReadImpl]oldUnit, [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.Version([CtLiteralImpl]"1.0.0"));
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]deserialized);
        [CtInvocationImpl][CtCommentImpl]// Spot check the values
        assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTime(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTime());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getActualTime(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getActualTime());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getCost(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getCost());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.isSameArmorType(), [CtInvocationImpl][CtVariableReadImpl]deserialized.isSameArmorType());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.hasFailedCheck(), [CtInvocationImpl][CtVariableReadImpl]deserialized.hasFailedCheck());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getRefitClass(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getRefitClass());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTimeSpent(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTimeSpent());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTimeLeft(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTimeLeft());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.isCustomJob(), [CtInvocationImpl][CtVariableReadImpl]deserialized.isCustomJob());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.kitFound(), [CtInvocationImpl][CtVariableReadImpl]deserialized.kitFound());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.isBeingRefurbished(), [CtInvocationImpl][CtVariableReadImpl]deserialized.isBeingRefurbished());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getTech().getId(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getTech().getId());
        [CtLocalVariableImpl][CtCommentImpl]// Check that we got all the correct old parts in the XML
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> oldUnitParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getOldUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> serializedOldParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getOldUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]oldUnitParts, [CtVariableReadImpl]serializedOldParts);
        [CtLocalVariableImpl][CtCommentImpl]// Check that we got all the correct new parts in the XML
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> newUnitParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getNewUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> serializedNewParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getNewUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]newUnitParts, [CtVariableReadImpl]serializedNewParts);
        [CtLocalVariableImpl][CtCommentImpl]// Check that we got all the shopping list entries (by name, not amazing but reasonable)
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> shoppingList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getShoppingList().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getName()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> serializedShoppingList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getShoppingList().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getName()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtInvocationImpl][CtCommentImpl]// Make sure they're the same length first...
        assertEquals([CtInvocationImpl][CtVariableReadImpl]shoppingList.size(), [CtInvocationImpl][CtVariableReadImpl]serializedShoppingList.size());
        [CtForEachImpl][CtCommentImpl]// ...then make sure they're the "same" by removing them one by one...
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partName : [CtVariableReadImpl]shoppingList) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingList.remove([CtVariableReadImpl]partName));
        }
        [CtInvocationImpl][CtCommentImpl]// ...and ensuring nothing is left.
        assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingList.isEmpty());
        [CtLocalVariableImpl][CtCommentImpl]// Do the same for their descriptions, which include the quantities...
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> shoppingListDescs = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]refit.getShoppingListDescription());
        [CtLocalVariableImpl][CtCommentImpl]// ...except the second list needs to be mutable.
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> serializedShoppingListDescs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]deserialized.getShoppingListDescription()));
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]shoppingListDescs.size(), [CtInvocationImpl][CtVariableReadImpl]serializedShoppingListDescs.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String desc : [CtVariableReadImpl]shoppingListDescs) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingListDescs.remove([CtVariableReadImpl]desc));
        }
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingListDescs.isEmpty());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void fleaFLE4toFLE15Test() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Campaign mockCampaign = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Campaign.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.CampaignOptions mockCampaignOptions = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.CampaignOptions.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getCampaignOptions()).thenReturn([CtVariableReadImpl]mockCampaignOptions);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Warehouse mockWarehouse = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Warehouse.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getWarehouse()).thenReturn([CtVariableReadImpl]mockWarehouse);
        [CtLocalVariableImpl][CtCommentImpl]// Create the original entity backing the unit
        [CtTypeReferenceImpl]megamek.common.Entity oldEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getFleaFLE4();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.IPlayer mockPlayer = [CtInvocationImpl]mock([CtFieldReadImpl]megamek.common.IPlayer.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockPlayer.getName()).thenReturn([CtLiteralImpl]"Test Player");
        [CtInvocationImpl][CtVariableReadImpl]oldEntity.setOwner([CtVariableReadImpl]mockPlayer);
        [CtLocalVariableImpl][CtCommentImpl]// Create the entity we're going to refit to
        [CtTypeReferenceImpl]megamek.common.Entity newEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getFleaFLE15();
        [CtLocalVariableImpl][CtCommentImpl]// Create the unit which will be refit
        [CtTypeReferenceImpl]mekhq.campaign.unit.Unit oldUnit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtVariableReadImpl]oldEntity, [CtVariableReadImpl]mockCampaign);
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.setId([CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID());
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.initializeParts([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Create the Refit
        [CtTypeReferenceImpl]Refit refit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Refit([CtVariableReadImpl]oldUnit, [CtVariableReadImpl]newEntity, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]mockCampaign, [CtInvocationImpl][CtVariableReadImpl]refit.getCampaign());
        [CtInvocationImpl][CtCommentImpl]// 
        [CtCommentImpl]// Flea 4 to 15 Class D refit steps (in no particular order):
        [CtCommentImpl]// 1. Remove excess Large Laser (RA) [120 mins]
        [CtCommentImpl]// 2. Move Small Laser (LA) to (LT)(R) [120 mins]
        [CtCommentImpl]// 3. Move Small Laser (LA) to (RT)(R) [120 mins]
        [CtCommentImpl]// 4. Add Medium Laser (LA) [120 mins]
        [CtCommentImpl]// 5. Add Medium Laser (RA) [120 mins]
        [CtCommentImpl]// 6. Add Machine Gun (LA) [120 mins]
        [CtCommentImpl]// 7. Add Machine Gun (RA) [120 mins]
        [CtCommentImpl]// 8. Add Machine Gun Ammo Bin to (CT) [120 mins]
        [CtCommentImpl]// 9. Add 16 points of armor to 10 locations (except the HD).
        [CtCommentImpl]// a. Add 1 point to (LA) [5 mins]
        [CtCommentImpl]// b. Add 1 point to (RA) [5 mins]
        [CtCommentImpl]// c. Add 2 points to (LT) [10 mins]
        [CtCommentImpl]// d. Add 2 points to (RT) [10 mins]
        [CtCommentImpl]// e. Add 3 points to (CT) [15 mins]
        [CtCommentImpl]// g. Add 1 point to (LL) [5 mins]
        [CtCommentImpl]// h. Add 1 point to (RL) [5 mins]
        [CtCommentImpl]// i. Add 2 points to (RTL) [10 mins]
        [CtCommentImpl]// j. Add 2 points to (RTR) [10 mins]
        [CtCommentImpl]// k. Add 1 point to (RTC) [5 mins]
        [CtCommentImpl]// 10. Switch Flamer (CT) facing to (CT)(R) [120 mins]
        [CtCommentImpl]// 
        [CtCommentImpl]// Everything else is the same.
        [CtCommentImpl]// 
        [CtCommentImpl]// Per SO p188:
        [CtCommentImpl]// "This kit permits players to install a new item
        [CtCommentImpl]// where previously there was none..."
        assertEquals([CtTypeAccessImpl]Refit.CLASS_D, [CtInvocationImpl][CtVariableReadImpl]refit.getRefitClass());
        [CtInvocationImpl][CtCommentImpl]// Time?
        [CtCommentImpl]// + 1 removal @ 120 mins ea
        [CtCommentImpl]// + 2 moves @ 120 mins ea
        [CtCommentImpl]// + 1 facing change @ 120 mins ea
        [CtCommentImpl]// + 5 adds @ 120 mins ea
        [CtCommentImpl]// + 16 armor changes @ 5 mins ea
        [CtCommentImpl]// x 3 (Class D)
        assertEquals([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]120.0 * [CtLiteralImpl]9.0) + [CtBinaryOperatorImpl]([CtLiteralImpl]5.0 * [CtLiteralImpl]16.0)) * [CtLiteralImpl]3.0, [CtInvocationImpl][CtVariableReadImpl]refit.getActualTime(), [CtLiteralImpl]0.1);
        [CtInvocationImpl][CtCommentImpl]// Cost?
        [CtCommentImpl]// + 2 Medium Lasers @ 40,000 ea
        [CtCommentImpl]// + 2 Machine Guns @ 5,000 ea
        [CtCommentImpl]// + 1 ton Machine Gun Ammo @ 1,000 ea
        [CtCommentImpl]// + 1 ton Armor (Standard) @ 10,000 ea
        [CtCommentImpl]// x 1.1 (Refit Kit cost, SO p188)
        assertEquals([CtInvocationImpl][CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.finances.Money.of([CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtBinaryOperatorImpl]([CtLiteralImpl]40000.0 + [CtLiteralImpl]40000.0) + [CtLiteralImpl]5000.0) + [CtLiteralImpl]5000.0) + [CtLiteralImpl]1000.0) + [CtLiteralImpl]10000.0).multipliedBy([CtLiteralImpl]1.1), [CtInvocationImpl][CtVariableReadImpl]refit.getCost());
        [CtLocalVariableImpl][CtCommentImpl]// We're removing 1 Large Laser and using existing armor in 10 locations
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Part> removedParts = [CtInvocationImpl][CtVariableReadImpl]refit.getOldUnitParts();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]11, [CtInvocationImpl][CtVariableReadImpl]removedParts.size());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]removedParts.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.EquipmentPart) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"Large Laser")).count());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]10, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]removedParts.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]Armor).count());
        [CtLocalVariableImpl][CtCommentImpl]// All of the new parts should be from the old unit
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Part> newParts = [CtInvocationImpl][CtVariableReadImpl]refit.getNewUnitParts();
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]newParts.stream().allMatch([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getUnit().equals([CtVariableReadImpl]oldUnit)));
        [CtLocalVariableImpl][CtCommentImpl]// We need to buy two Medium Lasers, two Machine Guns, and Machine Gun Ammo
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]Part> shoppingCart = [CtInvocationImpl][CtVariableReadImpl]refit.getShoppingList();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]shoppingCart.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"Medium Laser")).count());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]2, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]shoppingCart.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.MissingEquipmentPart) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"Machine Gun")).count());
        [CtInvocationImpl]assertEquals([CtLiteralImpl]1, [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]shoppingCart.stream().filter([CtLambdaImpl]([CtParameterImpl] p) -> [CtBinaryOperatorImpl][CtBinaryOperatorImpl]([CtVariableReadImpl]p instanceof [CtTypeAccessImpl][CtTypeReferenceImpl]mekhq.campaign.parts.equipment.AmmoBin) && [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]p.getName().equals([CtLiteralImpl]"Machine Gun Ammo Bin")).count());
        [CtInvocationImpl][CtCommentImpl]// We should have 16 points of standard armor on order
        assertNotNull([CtInvocationImpl][CtVariableReadImpl]refit.getNewArmorSupplies());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getNewArmorSupplies().getType(), [CtTypeAccessImpl]EquipmentType.T_ARMOR_STANDARD);
        [CtInvocationImpl]assertEquals([CtLiteralImpl]16, [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getNewArmorSupplies().getAmountNeeded());
    }

    [CtMethodImpl][CtAnnotationImpl]@org.junit.Test
    public [CtTypeReferenceImpl]void testFleaFLE4toFLE15WriteToXml() throws [CtTypeReferenceImpl]javax.xml.parsers.ParserConfigurationException, [CtTypeReferenceImpl]org.xml.sax.SAXException, [CtTypeReferenceImpl]java.io.IOException [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Campaign mockCampaign = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Campaign.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getEntities()).thenReturn([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>());
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.CampaignOptions mockCampaignOptions = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.CampaignOptions.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getCampaignOptions()).thenReturn([CtVariableReadImpl]mockCampaignOptions);
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.Warehouse mockWarehouse = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.Warehouse.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockCampaign.getWarehouse()).thenReturn([CtVariableReadImpl]mockWarehouse);
        [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl]doReturn([CtLiteralImpl]null).when([CtVariableReadImpl]mockWarehouse).findSparePart([CtInvocationImpl]any());
        [CtLocalVariableImpl][CtTypeReferenceImpl]mekhq.campaign.personnel.Person mockTech = [CtInvocationImpl]mock([CtFieldReadImpl]mekhq.campaign.personnel.Person.class);
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.UUID techId = [CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID();
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockTech.getId()).thenReturn([CtVariableReadImpl]techId);
        [CtLocalVariableImpl][CtCommentImpl]// Create the original entity backing the unit
        [CtTypeReferenceImpl]megamek.common.Entity oldEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getFleaFLE4();
        [CtLocalVariableImpl][CtTypeReferenceImpl]megamek.common.IPlayer mockPlayer = [CtInvocationImpl]mock([CtFieldReadImpl]megamek.common.IPlayer.class);
        [CtInvocationImpl][CtInvocationImpl]when([CtInvocationImpl][CtVariableReadImpl]mockPlayer.getName()).thenReturn([CtLiteralImpl]"Test Player");
        [CtInvocationImpl][CtVariableReadImpl]oldEntity.setOwner([CtVariableReadImpl]mockPlayer);
        [CtLocalVariableImpl][CtCommentImpl]// Create the entity we're going to refit to
        [CtTypeReferenceImpl]megamek.common.Entity newEntity = [CtInvocationImpl][CtTypeAccessImpl]mekhq.campaign.unit.UnitTestUtilities.getFleaFLE15();
        [CtLocalVariableImpl][CtCommentImpl]// Create the unit which will be refit
        [CtTypeReferenceImpl]mekhq.campaign.unit.Unit oldUnit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.campaign.unit.Unit([CtVariableReadImpl]oldEntity, [CtVariableReadImpl]mockCampaign);
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.setId([CtInvocationImpl][CtTypeAccessImpl]java.util.UUID.randomUUID());
        [CtInvocationImpl][CtVariableReadImpl]oldUnit.initializeParts([CtLiteralImpl]false);
        [CtLocalVariableImpl][CtCommentImpl]// Make sure the unit parts have an ID before we serialize them
        [CtTypeReferenceImpl]int partId = [CtLiteralImpl]1;
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]Part part : [CtInvocationImpl][CtVariableReadImpl]oldUnit.getParts()) [CtBlockImpl]{
            [CtInvocationImpl][CtVariableReadImpl]part.setId([CtUnaryOperatorImpl][CtVariableWriteImpl]partId++);
        }
        [CtLocalVariableImpl][CtCommentImpl]// Create the Refit
        [CtTypeReferenceImpl]Refit refit = [CtConstructorCallImpl]new [CtTypeReferenceImpl]Refit([CtVariableReadImpl]oldUnit, [CtVariableReadImpl]newEntity, [CtLiteralImpl]false, [CtLiteralImpl]false);
        [CtInvocationImpl][CtVariableReadImpl]refit.setTech([CtVariableReadImpl]mockTech);
        [CtInvocationImpl][CtVariableReadImpl]refit.addTimeSpent([CtLiteralImpl]60);[CtCommentImpl]// 1 hour of work!

        [CtLocalVariableImpl][CtCommentImpl]// Write the Refit XML
        [CtTypeReferenceImpl]java.io.StringWriter sw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.StringWriter();
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.io.PrintWriter pw = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.PrintWriter([CtVariableReadImpl]sw);
        [CtInvocationImpl][CtVariableReadImpl]refit.writeToXml([CtVariableReadImpl]pw, [CtLiteralImpl]0);
        [CtLocalVariableImpl][CtCommentImpl]// Get the Refit XML
        [CtTypeReferenceImpl]java.lang.String xml = [CtInvocationImpl][CtVariableReadImpl]sw.toString();
        [CtInvocationImpl]assertFalse([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]xml.trim().isEmpty());
        [CtLocalVariableImpl][CtCommentImpl]// Using factory get an instance of document builder
        [CtTypeReferenceImpl]javax.xml.parsers.DocumentBuilder db = [CtInvocationImpl][CtTypeAccessImpl]mekhq.MekHqXmlUtil.newSafeDocumentBuilder();
        [CtLocalVariableImpl][CtCommentImpl]// Parse using builder to get DOM representation of the XML file
        [CtTypeReferenceImpl]org.w3c.dom.Document xmlDoc = [CtInvocationImpl][CtVariableReadImpl]db.parse([CtConstructorCallImpl]new [CtTypeReferenceImpl]java.io.ByteArrayInputStream([CtInvocationImpl][CtVariableReadImpl]xml.getBytes()));
        [CtLocalVariableImpl][CtTypeReferenceImpl]org.w3c.dom.Element refitElt = [CtInvocationImpl][CtVariableReadImpl]xmlDoc.getDocumentElement();
        [CtInvocationImpl]assertEquals([CtLiteralImpl]"refit", [CtInvocationImpl][CtVariableReadImpl]refitElt.getNodeName());
        [CtLocalVariableImpl][CtCommentImpl]// Deserialize the refit
        [CtTypeReferenceImpl]Refit deserialized = [CtInvocationImpl][CtTypeAccessImpl]Refit.generateInstanceFromXML([CtVariableReadImpl]refitElt, [CtVariableReadImpl]oldUnit, [CtConstructorCallImpl]new [CtTypeReferenceImpl]mekhq.Version([CtLiteralImpl]"1.0.0"));
        [CtInvocationImpl]assertNotNull([CtVariableReadImpl]deserialized);
        [CtInvocationImpl][CtVariableReadImpl]deserialized.reCalc();
        [CtInvocationImpl][CtCommentImpl]// Spot check the values
        assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTime(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTime());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getActualTime(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getActualTime());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getCost(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getCost());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.isSameArmorType(), [CtInvocationImpl][CtVariableReadImpl]deserialized.isSameArmorType());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.hasFailedCheck(), [CtInvocationImpl][CtVariableReadImpl]deserialized.hasFailedCheck());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getRefitClass(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getRefitClass());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTimeSpent(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTimeSpent());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.getTimeLeft(), [CtInvocationImpl][CtVariableReadImpl]deserialized.getTimeLeft());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.isCustomJob(), [CtInvocationImpl][CtVariableReadImpl]deserialized.isCustomJob());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.kitFound(), [CtInvocationImpl][CtVariableReadImpl]deserialized.kitFound());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]refit.isBeingRefurbished(), [CtInvocationImpl][CtVariableReadImpl]deserialized.isBeingRefurbished());
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getTech().getId(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getTech().getId());
        [CtLocalVariableImpl][CtCommentImpl]// Check that we got all the correct old parts in the XML
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> oldUnitParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getOldUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> serializedOldParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getOldUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]oldUnitParts, [CtVariableReadImpl]serializedOldParts);
        [CtLocalVariableImpl][CtCommentImpl]// Check that we got all the correct new parts in the XML
        [CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> newUnitParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getNewUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.Set<[CtTypeReferenceImpl]java.lang.Integer> serializedNewParts = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getNewUnitParts().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getId()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toSet());
        [CtInvocationImpl]assertEquals([CtVariableReadImpl]newUnitParts, [CtVariableReadImpl]serializedNewParts);
        [CtLocalVariableImpl][CtCommentImpl]// Check that we got all the shopping list entries (by name, not amazing but reasonable)
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> shoppingList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getShoppingList().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getName()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> serializedShoppingList = [CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getShoppingList().stream().map([CtLambdaImpl]([CtParameterImpl] p) -> [CtInvocationImpl][CtVariableReadImpl]p.getName()).collect([CtInvocationImpl][CtTypeAccessImpl]java.util.stream.Collectors.toList());
        [CtInvocationImpl][CtCommentImpl]// Make sure they're the same length first...
        assertEquals([CtInvocationImpl][CtVariableReadImpl]shoppingList.size(), [CtInvocationImpl][CtVariableReadImpl]serializedShoppingList.size());
        [CtForEachImpl][CtCommentImpl]// ...then make sure they're the "same" by removing them one by one...
        for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String partName : [CtVariableReadImpl]shoppingList) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingList.remove([CtVariableReadImpl]partName));
        }
        [CtInvocationImpl][CtCommentImpl]// ...and ensuring nothing is left.
        assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingList.isEmpty());
        [CtLocalVariableImpl][CtCommentImpl]// Do the same for their descriptions, which include the quantities...
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> shoppingListDescs = [CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]refit.getShoppingListDescription());
        [CtLocalVariableImpl][CtCommentImpl]// ...except the second list needs to be mutable.
        [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]java.lang.String> serializedShoppingListDescs = [CtConstructorCallImpl]new [CtTypeReferenceImpl]java.util.ArrayList<>([CtInvocationImpl][CtTypeAccessImpl]java.util.Arrays.asList([CtInvocationImpl][CtVariableReadImpl]deserialized.getShoppingListDescription()));
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtVariableReadImpl]shoppingListDescs.size(), [CtInvocationImpl][CtVariableReadImpl]serializedShoppingListDescs.size());
        [CtForEachImpl]for ([CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.String desc : [CtVariableReadImpl]shoppingListDescs) [CtBlockImpl]{
            [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingListDescs.remove([CtVariableReadImpl]desc));
        }
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtVariableReadImpl]serializedShoppingListDescs.isEmpty());
        [CtInvocationImpl][CtCommentImpl]// Make sure the new armor is serialized/deserialized properly
        assertNotNull([CtInvocationImpl][CtVariableReadImpl]deserialized.getNewArmorSupplies());
        [CtInvocationImpl]assertTrue([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getNewArmorSupplies().isSameType([CtInvocationImpl][CtVariableReadImpl]deserialized.getNewArmorSupplies()));
        [CtInvocationImpl]assertEquals([CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]refit.getNewArmorSupplies().getAmountNeeded(), [CtInvocationImpl][CtInvocationImpl][CtVariableReadImpl]deserialized.getNewArmorSupplies().getAmountNeeded());
    }
}