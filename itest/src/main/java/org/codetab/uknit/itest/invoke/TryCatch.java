package org.codetab.uknit.itest.invoke;

/**
 * When same method is invoked in try as well as in catch then verify never
 * statement is generated for that method.
 *
 * TODO - rectify the error
 *
 * @author m
 *
 */
class TryCatch {

    private DataDao dataDao;
    private Document document;

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
