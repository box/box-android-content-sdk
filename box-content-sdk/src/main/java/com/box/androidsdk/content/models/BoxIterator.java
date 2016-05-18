package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A collection that contains a subset of items that are a part of a larger collection. The items within a partial collection begin at an offset within the full
 * collection and end at a specified limit. Note that the actual size of a partial collection may be less than its limit since the limit only specifies the
 * maximum size. For example, if there's a full collection with a size of 3, then a partial collection with offset 0 and limit 3 would be equal to a partial
 * collection with offset 0 and limit 100.
 *
 * @param <E> the type of elements in this partial collection.
 */
public abstract class BoxIterator<E extends BoxJsonObject> extends BoxJsonObject implements Iterable<E>, Serializable {

    private static final long serialVersionUID = 8036181424029520417L;

    public static final String FIELD_ORDER = "order";
    public static final String FIELD_TOTAL_COUNT = "total_count";
    public static final String FIELD_ENTRIES = "entries";
    public static final String FIELD_OFFSET = "offset";
    public static final String FIELD_LIMIT = "limit";


    public BoxIterator() {
        super();
    }

    public BoxIterator(JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void createFromJson(JsonObject object) {
        super.createFromJson(object);
    }

    /**
     * Gets the offset within the full collection where this collection's items begin.
     *
     * @return the offset within the full collection where this collection's items begin.
     */
    public Long offset() {
        return getPropertyAsLong(FIELD_OFFSET);
    }

    /**
     * Gets the maximum number of items within the full collection that begin at {@link #offset}.
     *
     * @return the maximum number of items within the full collection that begin at the offset.
     */
    public Long limit() {
        return getPropertyAsLong(FIELD_LIMIT);
    }

    /**
     * Gets the size of the full collection that this partial collection is based off of.
     *
     * @return the size of the full collection that this partial collection is based off of.
     */
    public Long fullSize() {
        return getPropertyAsLong(FIELD_TOTAL_COUNT);
    }

    public int size() {
        if (getEntries() == null) {
            return 0;
        } else {
            return getEntries().size();
        }
    }

    public ArrayList<E> getEntries(){
        return getPropertyAsJsonObjectArray(getObjectCreator(), FIELD_ENTRIES);
    }

    public E get(int index) {
        return (E)getAs(getObjectCreator(), index);
    }

    protected abstract BoxJsonObjectCreator<E> getObjectCreator();

    public E getAs(BoxJsonObjectCreator<E> creator, int index) {
        return getEntries().get(index);
    }

    public ArrayList<BoxOrder> getSortOrders() {
        return getPropertyAsJsonObjectArray(BoxJsonObject.getBoxJsonObjectCreator(BoxOrder.class), FIELD_ORDER);
    }

    public Iterator<E> iterator(){
        return getEntries() == null ? Collections.<E>emptyList().iterator() : getEntries().iterator();
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        JsonObject iterator = new JsonObject();
        List<String> properties = getPropertiesKeySet();
        for (String property : properties){
            if (!property.equals(FIELD_ENTRIES)){
                iterator.add(property, getPropertyValue(property));
            }
        }
        stream.writeUTF(iterator.toString());
        JsonArray array = getPropertyAsJsonArray(FIELD_ENTRIES);
        if (array == null || array.isEmpty()){
            stream.writeInt(-1);
        } else {
            stream.writeInt(size());
            for (int i=0; i < size(); i++){
                stream.writeUTF(array.get(i).toString());
            }
        }
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        createFromJson(stream.readUTF());
        int cap = stream.readInt();
        if (cap >= 0){
            set(FIELD_ENTRIES, new JsonArray());
            for (int i=0; i < cap; i++){
                String child = stream.readUTF();
                JsonObject object = JsonObject.readFrom(child);
                addInJsonArray(FIELD_ENTRIES, object);
            }
        }
    }

}
