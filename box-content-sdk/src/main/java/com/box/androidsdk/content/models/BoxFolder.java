package com.box.androidsdk.content.models;

import android.text.TextUtils;

import com.box.androidsdk.content.BoxConstants;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class that represents a folder on Box.
 */
public class BoxFolder extends BoxItem {

    private static final long serialVersionUID = 8020073615785970254L;

    public static final String TYPE = "folder";

    public static final String FIELD_FOLDER_UPLOAD_EMAIL = "folder_upload_email";
    public static final String FIELD_HAS_COLLABORATIONS = "has_collaborations";
    public static final String FIELD_SYNC_STATE = "sync_state";
    public static final String FIELD_CAN_NON_OWNERS_INVITE = "can_non_owners_invite";
    public static final String FIELD_ITEM_COLLECTION = "item_collection";
    public static final String FIELD_IS_EXTERNALLY_OWNED = "is_externally_owned";
    public static final String FIELD_ALLOWED_INVITEE_ROLES = "allowed_invitee_roles";
    public static final String FIELD_SIZE = BoxConstants.FIELD_SIZE;
    public static final String FIELD_CONTENT_CREATED_AT = BoxConstants.FIELD_CONTENT_CREATED_AT;
    public static final String FIELD_CONTENT_MODIFIED_AT = BoxConstants.FIELD_CONTENT_MODIFIED_AT;

    public static final String[] ALL_FIELDS = new String[]{
            FIELD_TYPE,
            FIELD_ID,
            FIELD_SEQUENCE_ID,
            FIELD_ETAG,
            FIELD_NAME,
            FIELD_CREATED_AT,
            FIELD_MODIFIED_AT,
            FIELD_DESCRIPTION,
            FIELD_SIZE,
            FIELD_PATH_COLLECTION,
            FIELD_CREATED_BY,
            FIELD_MODIFIED_BY,
            FIELD_TRASHED_AT,
            FIELD_PURGED_AT,
            FIELD_CONTENT_CREATED_AT,
            FIELD_CONTENT_MODIFIED_AT,
            FIELD_OWNED_BY,
            FIELD_SHARED_LINK,
            FIELD_FOLDER_UPLOAD_EMAIL,
            FIELD_PARENT,
            FIELD_ITEM_STATUS,
            FIELD_ITEM_COLLECTION,
            FIELD_SYNC_STATE,
            FIELD_HAS_COLLABORATIONS,
            FIELD_PERMISSIONS,
            FIELD_CAN_NON_OWNERS_INVITE,
            FIELD_IS_EXTERNALLY_OWNED,
            FIELD_ALLOWED_SHARED_LINK_ACCESS_LEVELS,
            FIELD_ALLOWED_INVITEE_ROLES,
            FIELD_COLLECTIONS,
    };

    /**
     * Constructs an empty BoxFolder object.
     */
    public BoxFolder() {
        super();
    }

    /**
     * Constructs a BoxFolder with the provided map values.
     *
     * @param map map of keys and values of the object.
     */
    public BoxFolder(Map<String, Object> map) {
        super(map);
    }

    /**
     * A convenience method to create an empty folder with just the id and type fields set. This allows
     * the ability to interact with the content sdk in a more descriptive and type safe manner
     *
     * @param folderId the id of folder to create
     * @return an empty BoxFolder object that only contains id and type information
     */
    public static BoxFolder createFromId(String folderId) {
        LinkedHashMap<String, Object> folderMap = new LinkedHashMap<String, Object>();
        folderMap.put(BoxItem.FIELD_ID, folderId);
        folderMap.put(BoxItem.FIELD_TYPE, BoxFolder.TYPE);
        return new BoxFolder(folderMap);
    }

    /**
     * Gets the upload email for the folder.
     *
     * @return the upload email for the folder.
     */
    public BoxUploadEmail getUploadEmail() {
        return (BoxUploadEmail) mProperties.get(FIELD_FOLDER_UPLOAD_EMAIL);
    }

    /**
     * Gets whether or not the folder has any collaborations.
     *
     * @return true if the folder has collaborations; otherwise false.
     */
    public Boolean getHasCollaborations() {
        return getPropertyAsBoolean(FIELD_HAS_COLLABORATIONS);
    }

    /**
     * Gets the sync state of the folder.
     *
     * @return the sync state of the folder.
     */
    public SyncState getSyncState() {
        return (SyncState) mProperties.get(FIELD_SYNC_STATE);
    }

    /**
     * Gets whether or not the non-owners can invite collaborators to the folder.
     *
     * @return whether or not the non-owners can invite collaborators to the folder.
     */
    public Boolean getCanNonOwnersInvite() {
        return getPropertyAsBoolean(FIELD_CAN_NON_OWNERS_INVITE);
    }

    /**
     * Gets collection of mini file, folder, and bookmark objects contained in this folder.
     *
     * @return list of mini item objects contained in the folder.
     */
    public BoxIteratorItems getItemCollection() {
        return this.mProperties.containsKey(FIELD_ITEM_COLLECTION) ?
                (BoxIteratorItems) this.mProperties.get(FIELD_ITEM_COLLECTION) :
                null;
    }

    /**
     * Gets whether this folder is owned by a user outside of the enterprise.
     *
     * @return whether this folder is owned externally.
     */
    public Boolean getIsExternallyOwned() {
        return getPropertyAsBoolean(FIELD_IS_EXTERNALLY_OWNED);
    }

    /**
     * Access level settings for shared links set by administrator. Can be collaborators, open, or company.
     *
     * @return array list of access levels that are allowed by the administrator.
     */
    public ArrayList<BoxSharedLink.Access> getAllowedSharedLinkAccessLevels() {
        return (ArrayList<BoxSharedLink.Access>) mProperties.get(FIELD_ALLOWED_SHARED_LINK_ACCESS_LEVELS);
    }

    /**
     * Folder collaboration settings allowed by the enterprise administrator.
     *
     * @return list of roles allowed for folder collaboration invitees.
     */
    public ArrayList<BoxCollaboration.Role> getAllowedInviteeRoles() {
        return (ArrayList<BoxCollaboration.Role>) mProperties.get(FIELD_ALLOWED_INVITEE_ROLES);
    }

    @Override
    public Date getContentCreatedAt() {
        return super.getContentCreatedAt();
    }

    @Override
    public Long getSize() {
        return super.getSize();
    }

    @Override
    public Date getContentModifiedAt() {
        return super.getContentModifiedAt();
    }


    @Override
    protected void parseJSONMember(JsonObject.Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_FOLDER_UPLOAD_EMAIL)) {
            BoxUploadEmail uploadEmail = new BoxUploadEmail();
            uploadEmail.createFromJson(value.asObject());
            this.mProperties.put(FIELD_FOLDER_UPLOAD_EMAIL, uploadEmail);
            return;
        } else if (memberName.equals(FIELD_HAS_COLLABORATIONS)) {
            this.mProperties.put(FIELD_HAS_COLLABORATIONS, value.asBoolean());
            return;
        } else if (memberName.equals(FIELD_SYNC_STATE)) {
            this.mProperties.put(FIELD_SYNC_STATE, SyncState.fromString(value.asString()));
            return;
        } else if (memberName.equals(FIELD_CAN_NON_OWNERS_INVITE)) {
            this.mProperties.put(FIELD_CAN_NON_OWNERS_INVITE, value.asBoolean());
            return;
        } else if (memberName.equals(FIELD_ITEM_COLLECTION)) {
            JsonObject jsonObject = value.asObject();
            BoxIteratorItems collection = new BoxIteratorItems();
            collection.createFromJson(jsonObject);
            this.mProperties.put(FIELD_ITEM_COLLECTION, collection);
            return;
        } else if (memberName.equals(FIELD_IS_EXTERNALLY_OWNED)) {
            this.mProperties.put(FIELD_IS_EXTERNALLY_OWNED, value.asBoolean());
            return;
        } else if (memberName.equals(FIELD_ALLOWED_INVITEE_ROLES)) {
            JsonArray rolesArr = value.asArray();
            ArrayList<BoxCollaboration.Role> allowedRoles = new ArrayList<BoxCollaboration.Role>();
            for (JsonValue val : rolesArr) {
                allowedRoles.add(BoxCollaboration.Role.fromString(val.asString()));
            }
            this.mProperties.put(FIELD_ALLOWED_INVITEE_ROLES, allowedRoles);
            return;
        }

        super.parseJSONMember(member);
    }

    /**
     * Enumerates the possible sync states that a folder can have.
     */
    public enum SyncState {
        /**
         * The folder is synced.
         */
        SYNCED("synced"),

        /**
         * The folder is not synced.
         */
        NOT_SYNCED("not_synced"),

        /**
         * The folder is partially synced.
         */
        PARTIALLY_SYNCED("partially_synced");

        private final String mValue;

        private SyncState(String value) {
            this.mValue = value;
        }

        public static SyncState fromString(String text) {
            if (!TextUtils.isEmpty(text)) {
                for (SyncState e : SyncState.values()) {
                    if (text.equalsIgnoreCase(e.toString())) {
                        return e;
                    }
                }
            }
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "No enum with text %s found", text));
        }

        @Override
        public String toString() {
            return this.mValue;
        }
    }
}