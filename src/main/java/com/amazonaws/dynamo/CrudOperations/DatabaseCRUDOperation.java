package com.amazonaws.dynamo.CrudOperations;


import com.amazonaws.dynamo.Bean.CustomerProfileSampleFields;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.amazonaws.dynamo.Constants.Constant.application_json;

@Getter
public class DatabaseCRUDOperation<T> implements Serializable {
    private static final long serialVersionUid = 1L;
    private static final ObjectMapper MAPPER;
    private CRUD crud;
    private T rowId;
    private Long crudTimestamp;
    private Map<String, Object> databaseFieldsToModify;
    private List<String> databaseFieldsToRetrieve;
    private long createdTimestamp;

    public static <T> DatabaseCRUDOperation<T> read(T rowId, Long crudTimestamp, List<String> databaseFieldsToRetrieve) {
        DatabaseCRUDOperation<T> request = (DatabaseCRUDOperation<T>) builder().crud(CRUD.READ).rowId(rowId).databaseFieldsToRetrieve(databaseFieldsToRetrieve).crudTimestamp(crudTimestamp).createdTimestamp(System.currentTimeMillis()).build();
        return request;
    }

    public static <T> DatabaseCRUDOperation<T> createAndUpdate(T rowId, Map<String, Object> databaseFieldsToModify, long crudTimestamp) {
        DatabaseCRUDOperation<T> request = (DatabaseCRUDOperation<T>) builder().crud(CRUD.CREATE_AND_UPDATE).rowId(rowId).databaseFieldsToModify(databaseFieldsToModify).crudTimestamp(validateCrudTimestamp(crudTimestamp)).createdTimestamp(System.currentTimeMillis()).build();
        return request;
    }


    public static <T> DatabaseCRUDOperation<T> update(T rowId, Map<String, Object> databaseFieldsToModify, long crudTimestamp) {
        DatabaseCRUDOperation<T> request = (DatabaseCRUDOperation<T>) builder().crud(CRUD.UPDATE).rowId(rowId).databaseFieldsToModify(databaseFieldsToModify).crudTimestamp(validateCrudTimestamp(crudTimestamp)).createdTimestamp(System.currentTimeMillis()).build();
        return request;
    }

    public static <T> DatabaseCRUDOperation<T> delete(T rowId, Map<String, Object> databaseFieldsToModify, long crudTimestamp) {
        DatabaseCRUDOperation<T> request = (DatabaseCRUDOperation<T>) builder().crud(CRUD.DELETE).databaseFieldsToModify(databaseFieldsToModify).rowId(rowId).crudTimestamp(validateCrudTimestamp(crudTimestamp)).createdTimestamp(System.currentTimeMillis()).build();
        return request;
    }

    private static Long validateCrudTimestamp(Long crudTimestamp) {
        if (crudTimestamp == null) {
            return System.currentTimeMillis();
        } else if (crudTimestamp < 0L) {
            throw new IllegalArgumentException("Crud Timestamp cannot be less than 0!");
        } else {
            return crudTimestamp;
        }
    }

    public ObjectNode toJson() {
        return (ObjectNode) MAPPER.valueToTree(this);
    }

    public String toString() {
        return this.toJson().toString();
    }

    public static void main(String[] args) throws IOException, NoSuchFieldException, ParseException, URISyntaxException, ExecutionException, InterruptedException {

        //insert fields


        //get fields
        List<String> listField = new ArrayList<>();
        listField.add("ts");
        listField.add("dt");
        listField.add("widKey");
        listField.add("userIntId");

        //delete fields
        Map<String, Object> deletefields = new HashMap<>();
        deletefields.put(CustomerProfileSampleFields.dt, null);
        deletefields.put(CustomerProfileSampleFields.widKey, null);

        //initial data insertion


        //delete request
        DatabaseCRUDOperation<Long> deleteRequest = DatabaseCRUDOperation.delete((long) 5123, deletefields, 114485197100L);

        //create or post request to insert data
//        DatabaseCRUDOperation<Long> postRequest2 = DatabaseCRUDOperation.createAndUpdate((long) 5123, postField,114485197100L);

        //update request with specific fields
//        DatabaseCRUDOperation<Long> updateRequest = DatabaseCRUDOperation.update((long) 4240342,updatefields,1701365477410L);

        //get request with timestamp field and date null
        DatabaseCRUDOperation<Long> readRequest1 = DatabaseCRUDOperation.read((long) 4240342, 1701365477355L, null);

        String webServerUrl = "https://gqq20l4j49.execute-api.eu-west-1.amazonaws.com/Dev/Customer/";

//        RestService.execute(webServerUrl,deleteRequest,application_json);
//        RestService.execute(webServerUrl,postRequest2,application_json);
//        RestService.execute(webServerUrl,updateRequest,application_json);
//        RestService.execute(webServerUrl,updateRequest,application_json);

        // post request
//        for (int i = 1; i < 100; i++) {
//            Map<String,Object> postFieldsMap = generateData(i);
//            DatabaseCRUDOperation<Long> postRequest = DatabaseCRUDOperation.createAndUpdate((long) (4240342+i), postFieldsMap,(114485197100L+i));
//            RestService.execute(webServerUrl,postRequest,application_json);
//        }

        // Update fields


        int records = 50;
        for (int i = 0; i < records; i++) {
            HashMap<String, Object> updateFields = new HashMap<>();
            updateFields.put(CustomerProfileSampleFields.widKey, "ABCD**" + i);
            updateFields.put("__customerProfileVersion", "number**" + i);
            updateFields.put("_restrictedRM_available_to_useFlag", "boolean**" + i);
            updateFields.put("accountRevalidationReqdFlag2", "boolean**" + i);
            updateFields.put("adminCreatedAcct", "boolean**" + i);
            updateFields.put("addr1", "string**" + i);
            updateFields.put("addr2", "string**" + i);
            updateFields.put("cppYear", "number**" + i);
            updateFields.put("antiMoneyLaundryVerifiedFlags3", "boolean**" + i);
            updateFields.put("authVerifiedFlag", "boolean**" + i);
            updateFields.put("bannedByRegulator", "boolean**" + i);
            updateFields.put("cppMonth", "number**" + i);
            updateFields.put("dormantAccountFlag2", "boolean**" + i);
            updateFields.put("facebookIntId", "number**" + i);
            DatabaseCRUDOperation<Long> updateRequestNew = DatabaseCRUDOperation.update((long) 4240343, updateFields, 114485197201L + i);

            RestService.execute(webServerUrl, updateRequestNew, application_json);
//            CompletableFuture<Set<Object>> resultFuture = new CompletableFuture<>();
//            CompletableFuture.runAsync(() -> {
//                Request response;
//                try {
//                    response = RestService.execute(webServerUrl,updateRequestNew,application_json);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                } catch (URISyntaxException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println("Web server response: " + response);
//            }).thenAccept((response) -> resultFuture.complete(Collections.singleton(null)));

//            Request body = RestService.execute(webServerUrl,updateRequestNew,application_json);
//            OkHttpResponseFuture callback = new OkHttpResponseFuture();
//            new OkHttpClient().newBuilder()
//                    .build().newCall(body).enqueue(callback);
//
//            callback.future.thenApply(response -> {
//                try (response) {
//                    return response;
//                }
//            });

//            CompletableFuture.runAsync(() -> {
//                try {



//            String sample = RequestBodyGenerator.generatePostBody(updateRequestNew);
//            HttpClientSample.send(sample,webServerUrl);


        }
//        System.out.println("===============================================");
//        System.out.println("total execution time for " + records + " records -> " + (float) (timeFinished - timstart) / 1000 + " seconds");
//        System.out.println("total execution for each record -> " + ((float) (timeFinished - timstart) / records) / 1000 + " seconds");

//        ReadFile readFile = new ReadFile("/Users/dubeyroh/Library/CloudStorage/OneDrive-TheStarsGroup/Desktop/Github/aws-dynamo/src/main/resources/Sample.txt");
//        readFile.readTextFile();


    }


    public static Map<String,Object> generateData(int i){
        Map<String,Object> postField = new HashMap<>();
        String numberValue = "number"+ i;
        String booleanValue = "boolean"+i;
        String stringValue = "string"+i;
        postField.put("__customerProfileVersion",numberValue);
        postField.put("_restrictedRM_available_to_useFlag",booleanValue);
        postField.put("accountClosedFlag",booleanValue);
        postField.put("accountConverted",booleanValue);
        postField.put("accountRevalidationReqdFlag2",booleanValue);
        postField.put("acctCreatedByPerfectPickFlag3",booleanValue);
        postField.put("addr1",stringValue);
        postField.put("addr2",stringValue);
        postField.put("adminCreatedAcct",booleanValue);
        postField.put("affiliatePaid",booleanValue);
        postField.put("ageVerifiedFlag",booleanValue);
        postField.put("amlDepositThresholdReachedFlag3",booleanValue);
        postField.put("amlDocsInProgressFlags3",booleanValue);
        postField.put("amlSuspendedFlags3",booleanValue);
        postField.put("antiMoneyLaundryVerifiedFlags3",booleanValue);
        postField.put("authVerifiedFlag",booleanValue);
        postField.put("autoAcceptPriceChangesFlag3",booleanValue);
        postField.put("autoAddressFlag2",booleanValue);
        postField.put("autoConvertBuyIn",booleanValue);
        postField.put("autoConvertWinnings",booleanValue);
        postField.put("bannedByRegulator",booleanValue);
        postField.put("barredForReturnFlag",booleanValue);
        postField.put("birthDate",stringValue);
        postField.put("boughtPMchips",booleanValue);
        postField.put("cashBannedFlag",booleanValue);
        postField.put("city",stringValue);
        postField.put("country",stringValue);
        postField.put("cppLifetime",numberValue);
        postField.put("cppMilliCents",numberValue);
        postField.put("cppMonth",numberValue);
        postField.put("cppYear",numberValue);
        postField.put("createdFromVirtualMachineFlag2",booleanValue);
        postField.put("currency",stringValue);
        postField.put("customerDueDiligenceFlag3",booleanValue);
        postField.put("cvlExcludedFlag3",booleanValue);
        postField.put("cvlOptedInFlag3",booleanValue);
        postField.put("dfsFirstUsageFlag2",booleanValue);
        postField.put("dormantAccountFlag2",booleanValue);
        postField.put("dormantByAutomationFlag2",booleanValue);
        postField.put("everHadElevatedRiskLevelFlag3",booleanValue);
        postField.put("email",stringValue);
        postField.put("emailAlwaysValidateFlag2",booleanValue);
        postField.put("emailAutoValidatedFlag2",booleanValue);
        postField.put("emailBouncedFlag",booleanValue);
        postField.put("emailUnauthorizedFlag2",booleanValue);
        postField.put("emailVerifiedFlag",booleanValue);
        postField.put("emLocale",numberValue);
        postField.put("facebookIntId",numberValue);
        postField.put("firstDepositFlag",booleanValue);
        postField.put("firstDeposit",numberValue);
        postField.put("firstName",stringValue);
        postField.put("firstSportsBetUserFlag3",booleanValue);
        postField.put("firstTransfer",numberValue);
        postField.put("fiscalCodeVerifiedFlag3",booleanValue);
        postField.put("flags",numberValue);
        postField.put("flags2",numberValue);
        postField.put("flags3",numberValue);
        postField.put("fpp",numberValue);
        postField.put("fppCents",numberValue);
        postField.put("fppOneTime2016IncreaseFlag2",booleanValue);
        postField.put("fraudulentAccountFlag3",booleanValue);
        postField.put("ftConversionDoneFlag3",booleanValue);
        postField.put("ftKYCTrustedUserFlag3",booleanValue);
        postField.put("fullName",stringValue);
        postField.put("GDPRreadyFlag3",booleanValue);
        postField.put("generateTempPinOnEachLoginFlag3",booleanValue);
        postField.put("guestAccount",booleanValue);
        postField.put("hadValidatedPaymentMethodFlag3",booleanValue);
        postField.put("hasActiveFastDeposit",booleanValue);
        postField.put("hasBuyinLimitEx",booleanValue);
        postField.put("hasCasinoGameLimit",booleanValue);
        postField.put("hasChallengeQuestionsFlag2",booleanValue);
        postField.put("hasDepositAttemptFlag2",booleanValue);
        postField.put("hasExtendedRelatedFlag3",booleanValue);
        postField.put("hasFirstTransferFlag",booleanValue);
        postField.put("hasFullTiltPairedAccount",booleanValue);
        postField.put("hasIndividualLimitsFlag",booleanValue);
        postField.put("hasLoginCoolOffLimitFlag3",booleanValue);
        postField.put("hasLoginTotalLimitFlag3",booleanValue);
        postField.put("hasLongSessionCoolOffLimitFlag3",booleanValue);
        postField.put("hasLossLimitFlag3",booleanValue);
        postField.put("hasMrmcAccountFlag",booleanValue);
        postField.put("hasNetDepositLimitFlag3",booleanValue);
        postField.put("hasNonPmBalance",booleanValue);
        postField.put("hasNonUsdAccountFlag",booleanValue);
        postField.put("hasPendingDepositLimit",booleanValue);
        postField.put("hasPendingGameLimit",booleanValue);
        postField.put("hasPlayingTimeLimit",booleanValue);
        postField.put("hasPokerDuelLimitFlag2",booleanValue);
        postField.put("hasPsExLossLimitFlag3",booleanValue);
        postField.put("hasRelatedFlag",booleanValue);
        postField.put("hasRmNokHeldDeposit",booleanValue);
        postField.put("hasRollbackHistoryCalculatedFlag3",booleanValue);
        postField.put("hasRollbackHistoryFlag3",booleanValue);
        postField.put("hasSideBetsLimitFlag3",booleanValue);
        postField.put("hasSpendLimitFlag3",booleanValue);
        postField.put("hasSportsAccumulatedLimitFlag2",booleanValue);
        postField.put("hasSportsLossLimitFlag3",booleanValue);
        postField.put("hasSportsSpendLimitFlag3",booleanValue);
        postField.put("hasSportsStakeLimit",booleanValue);
        postField.put("hasSuperRelated",booleanValue);
        postField.put("hasTableLimitFlag",booleanValue);
        postField.put("hasTournLimit",booleanValue);
        postField.put("hasTTicketsTotalCalculated",booleanValue);
        postField.put("hasUnclearedDepositsFlag",booleanValue);
        postField.put("hasWireAccountFlag",booleanValue);
        postField.put("hideCasinoGamesFlags2",booleanValue);
        postField.put("holdCashoutFlag",booleanValue);
        postField.put("homeGamesPlayer",booleanValue);
        postField.put("idVerifiedFlag3",booleanValue);
        postField.put("imageCheckedFlag",booleanValue);
        postField.put("imageId",numberValue);
        postField.put("inGracePeriod_obsoleteFlag",booleanValue);
        postField.put("intelliPokerRegisteredFlag",booleanValue);
        postField.put("ipAddrIsExemptFlag",booleanValue);
        postField.put("isAffiliateFlag",booleanValue);
        postField.put("itKeepRmMoneyOk",booleanValue);
        postField.put("itSuspendedClosed",booleanValue);
        postField.put("KYCCompletedFlag3",booleanValue);
        postField.put("kycRequiredFlag3",booleanValue);
        postField.put("lastDepositAmountLocalCurrency",numberValue);
        postField.put("lastDepositAmountUSD",numberValue);
        postField.put("lastDepositCompletedTime",stringValue);
        postField.put("lastDepositCurrency",stringValue);
        postField.put("lastName",stringValue);
        postField.put("licenseId",numberValue);
        postField.put("licenseMobileVerifiedFlag3",booleanValue);
        postField.put("limitedLoginFlag",booleanValue);
        postField.put("livenessVerifiedFlag3",booleanValue);
        postField.put("locale",numberValue);
        postField.put("loginBlockedGeoIpRuleFlags3",booleanValue);
        postField.put("marketBlackListFlag3",booleanValue);
        postField.put("mobileVerified",booleanValue);
        postField.put("needWelcomeEmail",booleanValue);
        postField.put("nemIdAuthenticated",booleanValue);
        postField.put("InstallIdForSmsDetected",booleanValue);
        postField.put("nickNameCheckedFlag",booleanValue);
        postField.put("noLoginEmailNotification",booleanValue);
        postField.put("noSMSPasswordReset",booleanValue);
        postField.put("occupationVerifiedFlag3",booleanValue);
        postField.put("pastGracePeriod_obsoleteFlag",booleanValue);
        postField.put("pepWatchScreenedAndValid",booleanValue);
        postField.put("permanentBlacklistedFlag3",booleanValue);
        postField.put("playedRMCasinoGame",booleanValue);
        postField.put("playedRMGame",booleanValue);
        postField.put("playedRmLiveCasinoGameFlag3",booleanValue);
        postField.put("playingTimeLimitReached",booleanValue);
        postField.put("priv2AffiliateDNPC",booleanValue);
        postField.put("priv2AffiliateDNPP",booleanValue);
        postField.put("priv2AffiliateDoNotPay",booleanValue);
        postField.put("priv2AlwaysHighRiskRestriction",booleanValue);
        postField.put("priv2BetaTester",booleanValue);
        postField.put("priv2BizAccount",booleanValue);
        postField.put("priv2BypassIpBlocking",booleanValue);
        postField.put("priv2CannotAddCurrencyAccount",booleanValue);
        postField.put("priv2CannotCreateHGClub",booleanValue);
        postField.put("priv2CannotJoinHGClub",booleanValue);
        postField.put("priv2CannotReceiveTransfer",booleanValue);
        postField.put("priv2CannotSendTransfer",booleanValue);
        postField.put("priv2CanRegisterMCTourns",booleanValue);
        postField.put("priv2ChatTempBannedByACM",booleanValue);
        postField.put("priv2DontShowPersonalInfo",booleanValue);
        postField.put("priv2ExcludeStellarReward",booleanValue);
        postField.put("priv2ExemptFrom3DSecure",booleanValue);
        postField.put("priv2HeadsUpTablesNotAllowed",booleanValue);
        postField.put("priv2HGNoEmails",booleanValue);
        postField.put("priv2HGShadowFounder",booleanValue);
        postField.put("priv2HideMobileIconToOthers",booleanValue);
        postField.put("priv2HideUserInfo",booleanValue);
        postField.put("priv2IgnoreMicroTransfers",booleanValue);
        postField.put("priv2LinkEmailToAccount",booleanValue);
        postField.put("priv2MobileMarketingMessages",booleanValue);
        postField.put("priv2MultiCurrBetaTester",booleanValue);
        postField.put("priv2NjTesting",booleanValue);
        postField.put("priv2NoAutoImageChange",booleanValue);
        postField.put("priv2NoAutomatedUnregister",booleanValue);
        postField.put("priv2NoCasinoLimitIncrease",booleanValue);
        postField.put("priv2NoCasinoPlay",booleanValue);
        postField.put("priv2NoDepositLimitIncrease",booleanValue);
        postField.put("priv2NoFastDeposit",booleanValue);
        postField.put("priv2NoHeadsUpSitNGo",booleanValue);
        postField.put("priv2NoHighRiskRestriction",booleanValue);
        postField.put("priv2NoHyperTurboSitNGo",booleanValue);
        postField.put("priv2NoLiveAssistance",booleanValue);
        postField.put("priv2NoPotLimitOmahaCashGames",booleanValue);
        postField.put("priv2NoPwdChangeReminder",booleanValue);
        postField.put("priv2NoSagTourns",booleanValue);
        postField.put("priv2NoSelfPLI",booleanValue);
        postField.put("priv2NoSetupCQPrompt",booleanValue);
        postField.put("priv2NoSMSPasswordReset",booleanValue);
        postField.put("priv2NoTableLimitIncrease",booleanValue);
        postField.put("priv2NoTournLimitIncrease",booleanValue);
        postField.put("priv2NotQualifiedFirstTransfer",booleanValue);
        postField.put("priv2NoZoomGames",booleanValue);
        postField.put("priv2Obsolete",booleanValue);
        postField.put("priv2OneTimeBypassConvLimits",booleanValue);
        postField.put("priv2PermanentClosuse",booleanValue);
        postField.put("priv2PSRelated",booleanValue);
        postField.put("priv2Reserved6",booleanValue);
        postField.put("priv2Reserved7",booleanValue);
        postField.put("priv2SafeDepositMode",booleanValue);
        postField.put("priv2ShowNoChatTooltip",booleanValue);
        postField.put("priv2ShowVipStatusToOthers",booleanValue);
        postField.put("priv2SuspendRM",booleanValue);
        postField.put("priv2TeamPro",booleanValue);
        postField.put("priv2TransferAffiliate",booleanValue);
        postField.put("priv2UserGlobalMute",booleanValue);
        postField.put("priv3AvailableForReuse",booleanValue);
        postField.put("priv3BadBeatCutoff",booleanValue);
        postField.put("priv3BlockPMPurchase",booleanValue);
        postField.put("priv3BypassMAAAutoActions",booleanValue);
        postField.put("priv3BypassNAC2",booleanValue);
        postField.put("priv3BypassOTP",booleanValue);
        postField.put("priv3CasinoPremiumPlayer",booleanValue);
        postField.put("priv3CasinoVIP",booleanValue);
        postField.put("priv3DoNotPayBonus",booleanValue);
        postField.put("priv3ExtRegulator",booleanValue);
        postField.put("priv3FppBundles",booleanValue);
        postField.put("priv3IncidentReportFiled",booleanValue);
        postField.put("priv3KeepYoungUserGroup",booleanValue);
        postField.put("priv3NoAllInCashout",booleanValue);
        postField.put("priv3NoCashoutCancellation",booleanValue);
        postField.put("priv3NoDFSPlay",booleanValue);
        postField.put("priv3NoEwlSportsPlay",booleanValue);
        postField.put("priv3NoHTML5Client",booleanValue);
        postField.put("priv3NoKOTourns",booleanValue);
        postField.put("priv3NoLeaderBoard",booleanValue);
        postField.put("priv3NoLossLimitIncrease",booleanValue);
        postField.put("priv3NoMailListCasino",booleanValue);
        postField.put("priv3NoMailListPoker",booleanValue);
        postField.put("priv3NoMailListSports",booleanValue);
        postField.put("priv3NoPlayTimeIncrease",booleanValue);
        postField.put("priv3NoPowerUpPlay",booleanValue);
        postField.put("priv3NoSideBets",booleanValue);
        postField.put("priv3NoSportLiveAlert",booleanValue);
        postField.put("priv3NoSportsBonus",booleanValue);
        postField.put("priv3NoSportsLimitIncrease",booleanValue);
        postField.put("priv3NoSportsPlay",booleanValue);
        postField.put("priv3NoSuggestedDeposit",booleanValue);
        postField.put("priv3NoSurveyInvites",booleanValue);
        postField.put("priv3NoUnfoldCashGames",booleanValue);
        postField.put("priv3NoUpsell",booleanValue);
        postField.put("priv3NoWithdrawal",booleanValue);
        postField.put("priv3PMPremiumPlayer",booleanValue);
        postField.put("priv3PokerPremiumPlayer",booleanValue);
        postField.put("priv3PspcDisplay",booleanValue);
        postField.put("priv3SportsPremiumPlayer",booleanValue);
        postField.put("priv3SportsVip",booleanValue);
        postField.put("priv3SportTrader",booleanValue);
        postField.put("priv3StaffPlayAccount",booleanValue);
        postField.put("priv3UsesCompetitor",booleanValue);
        postField.put("privAdmin",booleanValue);
        postField.put("privBonusAfterCashout",booleanValue);
        postField.put("privBypassVelocityControl",booleanValue);
        postField.put("privChangeImage",booleanValue);
        postField.put("privChat",booleanValue);
        postField.put("privDoNotSendTournWinnerEmail",booleanValue);
        postField.put("privDontShowWhere",booleanValue);
        postField.put("privDuplicate",booleanValue);
        postField.put("privEarlyCashout",booleanValue);
        postField.put("privHidePersonalInfo",booleanValue);
        postField.put("privHighRoller",booleanValue);
        postField.put("privileges",numberValue);
        postField.put("privileges2",numberValue);
        postField.put("privileges3",numberValue);
        postField.put("privInstaClearCashout",booleanValue);
        postField.put("privNoAutoBan",booleanValue);
        postField.put("privNoBanByCountry",booleanValue);
        postField.put("privNoCityChange",booleanValue);
        postField.put("privNoMailList",booleanValue);
        postField.put("privNotSpendUncleared",booleanValue);
        postField.put("privPlay",booleanValue);
        postField.put("privRequestTourn",booleanValue);
        postField.put("privRollbackOverride",booleanValue);
        postField.put("privSelfExcluded",booleanValue);
        postField.put("privShowImage",booleanValue);
        postField.put("privSuperHighRoller",booleanValue);
        postField.put("privTestCashier",booleanValue);
        postField.put("privTimeoutAllIn",booleanValue);
        postField.put("privTMoneyTrader",booleanValue);
        postField.put("privTransferVerified",booleanValue);
        postField.put("privVip",booleanValue);
        postField.put("privVipTest",booleanValue);
        postField.put("pspGenerated",booleanValue);
        postField.put("pspRequiredFlag",booleanValue);
        postField.put("pwdResetCodeGeneratedFlag3",booleanValue);
        postField.put("rateMobileAppFlag2",booleanValue);
        postField.put("realMoneyOkFlag",booleanValue);
        postField.put("registered",numberValue);
        postField.put("registeredInRegulatorFlag3",booleanValue);
        postField.put("RMPlayerDepositTransferFlag",booleanValue);
        postField.put("rsaActivated",booleanValue);
        postField.put("rsaRequiredFlag",booleanValue);
        postField.put("security3DSTriggerFlag3",booleanValue);
        postField.put("sex",stringValue);
        postField.put("showAMLVerifiedModalFlag3",booleanValue);
        postField.put("showSelfExclusionExpiredDialogFlag3",booleanValue);
        postField.put("smsMarketingFlag",booleanValue);
        postField.put("smsValidationForNewInstallId",booleanValue);
        postField.put("sppLifetime",numberValue);
        postField.put("sppMonth",numberValue);
        postField.put("sppYear",numberValue);
        postField.put("state",stringValue);
        postField.put("strongAuthReqd",booleanValue);
        postField.put("subsequentAccount",booleanValue);
        postField.put("tempPINFromAdminMassAssignFlag3",booleanValue);
        postField.put("tempPINFromFailedLoginsFlag3",booleanValue);
        postField.put("tempPINFromNewDeviceFlag3",booleanValue);
        postField.put("timezone",numberValue);
        postField.put("usedSelfExclusionFlag",booleanValue);
        postField.put("userHasDummyEmailFlag2",booleanValue);
        postField.put("userId",stringValue);
        postField.put("userIsArchivedFlag2",booleanValue);
        postField.put("valuePropMessageDisplayedFlags3",booleanValue);
        postField.put("verifiedByRegulator_NEVER_USED",booleanValue);
        postField.put("verifiedForMarketingFlag3",booleanValue);
        postField.put("vppMinimumEarnedInternal",booleanValue);
        postField.put("walletCAD",numberValue);
        postField.put("walletEUR",numberValue);
        postField.put("walletGBP",numberValue);
        postField.put("walletINR",numberValue);
        postField.put("walletRON",numberValue);
        postField.put("walletSEK",numberValue);
        postField.put("walletUSD",numberValue);
        postField.put("zipCode",stringValue);

        return postField;
    }

    public static <T> DatabaseCRUDOperationBuilder<T> builder() {
        return new DatabaseCRUDOperationBuilder();
    }

    public DatabaseCRUDOperation() {
    }

    public DatabaseCRUDOperation(CRUD crud, T rowId, Long crudTimestamp, Map<String, Object> databaseFieldsToModify, List<String> databaseFieldsToRetrieve, long createdTimestamp) {
        this.crud = crud;
        this.rowId = rowId;
        this.crudTimestamp = crudTimestamp;
        this.databaseFieldsToModify = databaseFieldsToModify;
        this.databaseFieldsToRetrieve = databaseFieldsToRetrieve;
        this.createdTimestamp = createdTimestamp;
    }

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        MAPPER = objectMapper;
    }

    public static class DatabaseCRUDOperationBuilder<T> {
        private CRUD crud;
        private T rowId;
        private Long crudTimestamp;
        private Map<String, Object> databaseFieldsToModify;
        private List<String> databaseFieldsToRetrieve;
        private long createdTimestamp;

        DatabaseCRUDOperationBuilder() {
        }

        public DatabaseCRUDOperationBuilder<T> crud(CRUD crud) {
            this.crud = crud;
            return this;
        }

        public DatabaseCRUDOperationBuilder<T> rowId(T rowId) {
            this.rowId = rowId;
            return this;
        }

        public DatabaseCRUDOperationBuilder<T> crudTimestamp(Long crudTimestamp) {
            this.crudTimestamp = crudTimestamp;
            return this;
        }

        public DatabaseCRUDOperationBuilder<T> databaseFieldsToModify(Map<String, Object> databaseFieldsToModify) {
            this.databaseFieldsToModify = databaseFieldsToModify;
            return this;
        }

        public DatabaseCRUDOperationBuilder<T> databaseFieldsToRetrieve(List<String> databaseFieldsToRetrieve) {
            this.databaseFieldsToRetrieve = databaseFieldsToRetrieve;
            return this;
        }

        public DatabaseCRUDOperationBuilder<T> createdTimestamp(long createdTimestamp) {
            this.createdTimestamp = createdTimestamp;
            return this;
        }

        public DatabaseCRUDOperation<T> build() {
            return new DatabaseCRUDOperation(this.crud, this.rowId, this.crudTimestamp, this.databaseFieldsToModify, this.databaseFieldsToRetrieve, this.createdTimestamp);
        }

        public String toString() {
            return "DatabaseCRUDOperation.DatabaseCRUDOperationBuilder(crud=" + this.crud + ", rowId=" + this.rowId +", crudTimestamp=" + this.crudTimestamp + ", databaseFieldsToModify=" + this.databaseFieldsToModify + ", databaseFieldsToRetrieve=" + this.databaseFieldsToRetrieve + ", createdTimestamp=" + this.createdTimestamp + ")";
        }
    }
}

