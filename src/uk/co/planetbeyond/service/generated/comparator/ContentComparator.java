




// ______________________________________________________
// Generated by sql2java - http://sql2java.sourceforge.net/
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
//
// Author: Javed Kansi
// ______________________________________________________

package uk.co.planetbeyond.service.generated.comparator;

import java.util.Comparator;
import uk.co.planetbeyond.service.generated.ContentBean;
import uk.co.planetbeyond.service.generated.ContentManager;


/**
 * Comparator class is used to sort the ContentBean objects.
 * @author sql2java
 */
public class ContentComparator implements Comparator
{
    /**
     * Holds the field on which the comparison is performed.
     */
    private int iType;
    /**
     * Value that will contain the information about the order of the sort: normal or reversal.
     */
    private boolean bReverse;

    /**
     * Constructor class for ContentComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new ContentComparator(ContentManager.ID_CREATION_TIMESTAMP, bReverse));<code>
     *
     * @param iType the field from which you want to sort
     * <br>
     * Possible values are:
     * <ul>
     *   <li>ContentManager.ID_CREATION_TIMESTAMP
     *   <li>ContentManager.ID_FILE_NAME
     *   <li>ContentManager.ID_CONTENT_CATEGORY
     *   <li>ContentManager.ID_CONTENT_ID
     * </ul>
     */
    public ContentComparator(int iType)
    {
        this(iType, false);
    }

    /**
     * Constructor class for ContentComparator.
     * <br>
     * Example:
     * <br>
     * <code>Arrays.sort(pArray, new ContentComparator(ContentManager.ID_CREATION_TIMESTAMP, bReverse));<code>
     *
     * @param iType the field from which you want to sort.
     * <br>
     * Possible values are:
     * <ul>
     *   <li>ContentManager.ID_CREATION_TIMESTAMP
     *   <li>ContentManager.ID_FILE_NAME
     *   <li>ContentManager.ID_CONTENT_CATEGORY
     *   <li>ContentManager.ID_CONTENT_ID
     * </ul>
     *
     * @param bReverse set this value to true, if you want to reverse the sorting results
     */
    public ContentComparator(int iType, boolean bReverse)
    {
        this.iType = iType;
        this.bReverse = bReverse;
    }

    /**
     * Implementation of the compare method.
     */
    public int compare(Object pObj1, Object pObj2)
    {
        ContentBean b1 = (ContentBean)pObj1;
        ContentBean b2 = (ContentBean)pObj2;
        int iReturn = 0;
        switch(iType)
        {
            case ContentManager.ID_CREATION_TIMESTAMP:
                if (b1.getCreationTimestamp() == null && b2.getCreationTimestamp() != null) {
                    iReturn = -1;
                } else if (b1.getCreationTimestamp() == null && b2.getCreationTimestamp() == null) {
                    iReturn = 0;
                } else if (b1.getCreationTimestamp() != null && b2.getCreationTimestamp() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getCreationTimestamp().compareTo(b2.getCreationTimestamp());
                }
                break;
            case ContentManager.ID_FILE_NAME:
                if (b1.getFileName() == null && b2.getFileName() != null) {
                    iReturn = -1;
                } else if (b1.getFileName() == null && b2.getFileName() == null) {
                    iReturn = 0;
                } else if (b1.getFileName() != null && b2.getFileName() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getFileName().compareTo(b2.getFileName());
                }
                break;
            case ContentManager.ID_CONTENT_CATEGORY:
                if (b1.getContentCategory() == null && b2.getContentCategory() != null) {
                    iReturn = -1;
                } else if (b1.getContentCategory() == null && b2.getContentCategory() == null) {
                    iReturn = 0;
                } else if (b1.getContentCategory() != null && b2.getContentCategory() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getContentCategory().compareTo(b2.getContentCategory());
                }
                break;
            case ContentManager.ID_CONTENT_ID:
                if (b1.getContentId() == null && b2.getContentId() != null) {
                    iReturn = -1;
                } else if (b1.getContentId() == null && b2.getContentId() == null) {
                    iReturn = 0;
                } else if (b1.getContentId() != null && b2.getContentId() == null) {
                    iReturn = 1;
                } else {
                    iReturn = b1.getContentId().compareTo(b2.getContentId());
                }
                break;
            default:
                throw new IllegalArgumentException("Type passed for the field is not supported");
        }

        return bReverse ? (-1 * iReturn) : iReturn;
    }}