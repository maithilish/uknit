package org.codetab.uknit.itest.invoke;

/**
 * When same method is invoked in try as well as in catch then verify never
 * statement is generated for that method.
 *
 * TODO - rectify the error
 *
 * @author Maithilish
 *
 */
class TryCatch {

    private DataDao dataDao;
    private Document document;

    /*
     * STEPIN - document.getLocatorId() is called once in try block and in the
     * test for that path change verify from never() to times(1). In Exception
     * path test throw IllegalStateException.
     *
     */
    public void callSameMethodInTryAndCatch() {
        try {
            dataDao.get(document.getLocatorId());
        } catch (IllegalStateException e) {
            dataDao.delete(document.getLocatorId());
        }
    }

    interface DataDao {
        Object get(long locatorId);

        void delete(long locatorId);
    }

    interface Document {
        long getLocatorId();
    }
}
