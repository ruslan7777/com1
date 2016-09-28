package com.server.dao;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.server.hibernate.util.HibernateAnnotationUtil;
import com.shared.model.ClientSession;
import com.shared.model.DatePoint;
import com.shared.model.HourCostModel;
import com.shared.model.MoreLessUnlimModel;
import com.shared.model.SessionPseudoName;
import com.shared.model.SettingsHolder;
import com.shared.model.User;
import com.shared.utils.UserUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 8/8/16
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientSessionHibernateDaoImpl implements ClientSessionDao{
//    Map<String,SessionPseudoName> pseudoNamesMap = new HashMap<>();
//    Map<Long, ClientSession> clientSessionMap = new HashMap<>();
//    Map<Long, User> usersMap = new HashMap<>();
//    Map<Long, SettingsHolder> settingsHolderMap = new HashMap<>();
//    Map<Long, HourCostModel> hourCostModelMap = new HashMap<>();
//    Map<Long, MoreLessUnlimModel> moreLessUnlimModelMap = new HashMap<>();

    public ClientSessionHibernateDaoImpl() {

    }

    private void addTestClientSession(User testUser, long startTime, long stopTime, ClientSession.SESSION_STATUS sessionStatus, Long finalSum, Session session) {
        ClientSession testClientSession = new ClientSession(startTime, stopTime, testUser.getUserId());
//        testClientSession.setId(getMaxId() + 1);
        testClientSession.setCreationTime(startTime - 70000);
        testClientSession.setFinalSum(finalSum);
        SessionPseudoName removedTestSessionPseudoName12 = new SessionPseudoName("testName" + testClientSession.getId());
        addName(removedTestSessionPseudoName12);
        removedTestSessionPseudoName12.setIsUsed(true);
        testClientSession.setSessionPseudoName(removedTestSessionPseudoName12.getName());
        testClientSession.setStatus(sessionStatus);
        session.save(testClientSession);
    }

    @Override
    public List<SessionPseudoName> getFreePseudoNames() {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from com.shared.model.SessionPseudoName");
            List<SessionPseudoName> sessionPseudoNames = query.list();
            transaction.commit();
            return sessionPseudoNames;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void markNameAsFree(SessionPseudoName name) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SessionPseudoName sessionPseudoName = (SessionPseudoName) session.get(SessionPseudoName.class, name.getId());
            sessionPseudoName.setIsUsed(false);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void markNameAsUsed(SessionPseudoName name) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SessionPseudoName sessionPseudoName = (SessionPseudoName) session.get(SessionPseudoName.class, name.getId());
            sessionPseudoName.setIsUsed(true);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void addNames(List<SessionPseudoName> pseudoNamesList) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            for (SessionPseudoName name : pseudoNamesList) {
                session.save(name);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<ClientSession> saveClientSession(DatePoint datePoint, ClientSession clientSession, boolean isShowRemoved,
                                                 boolean isShowPayed) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            clientSession.setUserId(UserUtils.INSTANCE.getCurrentUser().getUserId());
            clientSession.setStartTime(System.currentTimeMillis());
            markNameAsUsed(new SessionPseudoName(clientSession.getSessionPseudoName()));
            session.save(clientSession);
            transaction.commit();
            return getClientSessionsList(datePoint, UserUtils.INSTANCE.getCurrentUser(), isShowRemoved, isShowPayed);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<ClientSession> saveHiberClientSession(DatePoint datePoint, ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn) {
        return null;
    }

    @Override
    public List<ClientSession> removeClientSession(DatePoint datePoint, ClientSession clientSession, boolean isShowRemoved, boolean showPayedOn) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ClientSession clientSessionFromDb = (ClientSession) session.get(clientSession.getClass(), clientSession.getId());
            clientSessionFromDb.setStatus(ClientSession.SESSION_STATUS.REMOVED);
            clientSessionFromDb.setFinalSum(0l);
            markNameAsFree(new SessionPseudoName(clientSession.getSessionPseudoName()));
            transaction.commit();
            return getClientSessionsList(datePoint, UserUtils.INSTANCE.getCurrentUser(), isShowRemoved, showPayedOn);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<ClientSession> getClientSessionsList(final DatePoint datePoint, User currentUser, final boolean isShowRemoved, final boolean showPayedOn) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from com.shared.model.ClientSession");
            List<ClientSession> clientSessions = query.list();
            Predicate<ClientSession> removedPredicate = new Predicate<ClientSession>() {
                @Override
                public boolean apply(ClientSession clientSession) {
                    return isShowRemoved || ClientSession.SESSION_STATUS.REMOVED != clientSession.getSessionStatus();
                }
            };
            Predicate<ClientSession> payedPredicate = new Predicate<ClientSession>() {
                @Override
                public boolean apply(ClientSession clientSession) {
                    return showPayedOn || ClientSession.SESSION_STATUS.PAYED != clientSession.getSessionStatus();
                }
            };
            Predicate<ClientSession> datePointPredicate = new Predicate<ClientSession>() {
                @Override
                public boolean apply(ClientSession clientSession) {
                    Date comparedDate = new Date();
                    long comparedTime;
                    Calendar c = Calendar.getInstance();
                    c.setTime(comparedDate);
                    c.add(Calendar.DATE, datePoint.getShiftValue());
                    c.set(Calendar.HOUR, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    comparedTime = c.getTime().getTime();
                    return clientSession.getStartTime() > comparedTime;
                }
            };
            Collection<ClientSession> filteredByRemoveList = Collections2.filter(clientSessions, removedPredicate);
            Collection<ClientSession> clientSessionCollections = Collections2.filter(filteredByRemoveList, payedPredicate);
            Collection<ClientSession> filteredByDateCollections = Collections2.filter(clientSessionCollections, datePointPredicate);
            ArrayList<ClientSession> filteredCollections = new ArrayList<>(filteredByDateCollections);
            Collections.sort(filteredCollections);
            transaction.commit();
            return filteredCollections;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<ClientSession> stopClientSession(DatePoint datePoint, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ClientSession clientSessionFromDb = (ClientSession) session.get(clientSession.getClass(), clientSession.getId());
            clientSessionFromDb.setStatus(ClientSession.SESSION_STATUS.STOPPED);
            clientSessionFromDb.setStopTime(clientSession.getStopTime());
            clientSessionFromDb.setFinalSum(clientSession.getFinalSum());
            transaction.commit();
            return getClientSessionsList(datePoint, UserUtils.INSTANCE.getCurrentUser(), toShowRemoved, toShowPayed);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<ClientSession> payClientSession(DatePoint datePoint, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ClientSession clientSessionFromDb = (ClientSession) session.get(clientSession.getClass(), clientSession.getId());
            clientSessionFromDb.setStatus(ClientSession.SESSION_STATUS.PAYED);
            markNameAsFree(new SessionPseudoName(clientSessionFromDb.getSessionPseudoName()));
            transaction.commit();
            return getClientSessionsList(datePoint, UserUtils.INSTANCE.getCurrentUser(), toShowRemoved, toShowPayed);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<SessionPseudoName> getAllPseudoNames() {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from com.shared.model.SessionPseudoName");
            List<SessionPseudoName> sessionPseudoNames = query.list();
            transaction.commit();
            return sessionPseudoNames;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void addName(SessionPseudoName namesTextBoxValue) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(namesTextBoxValue);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeName(SessionPseudoName sessionPseudoName) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(sessionPseudoName);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public User getCurrentUser(String userName, String userPassword) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Query query = session.createQuery("from com.shared.model.User u");
            User loggedUser = (User) query.uniqueResult();
            if (loggedUser != null) {
                Query settingsHolderQuery = session.createQuery("from com.shared.model.SettingsHolder");
//                settingsHolderQuery.setParameter("loggedUserId", loggedUser.getUserId());
                SettingsHolder settingsHolder = (SettingsHolder) settingsHolderQuery.uniqueResult();
                UserUtils.init();
                UserUtils.setSettings(settingsHolder);
                UserUtils.INSTANCE.setCurrentUser(loggedUser);
            }
            transaction.commit();
            return loggedUser;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public User saveUser(User user) {
//        User savedUser = usersMap.get(user.getUserId());
        SettingsHolder settingsHolder = UserUtils.getSettings();
//        settingsHolder.setFirstPartLength(user.getSettings().getFirstPartLength());
//        settingsHolder.setFirstPartSumAmount(user.getSettings().getFirstPartSumAmount());
//        UserUtils.INSTANCE.setHourCostModelMap(user.getSettings().getHourCostModelMap());
//        settingsHolder.setMoreLessUnlimModelMap(user.getSettings().getMoreLessUnlimModelMap());
        return null;
    }

    @Override
    public User login(String userName, String userPassword) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            populateDB(session);

            Query query = session.createQuery("from com.shared.model.User u");
            User loggedUser = (User) query.uniqueResult();
            if (loggedUser != null) {
                Query settingsHolderQuery = session.createQuery("from com.shared.model.SettingsHolder");
//                settingsHolderQuery.setParameter("loggedUserId", loggedUser.getUserId());
                SettingsHolder settingsHolder = (SettingsHolder) settingsHolderQuery.uniqueResult();
                UserUtils.init();
                UserUtils.setSettings(settingsHolder);
                UserUtils.INSTANCE.setCurrentUser(loggedUser);
            }
            transaction.commit();
            return loggedUser;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private void populateDB(Session session) {
        User testUser = new User();
//        testUser.setUserId(0l);
        testUser.setUserName("a");
        testUser.setPassword("");
        session.save(testUser);

        SettingsHolder testSettingsHolder = new SettingsHolder();
        testSettingsHolder.setFirstPartLength(20000l);
        testSettingsHolder.setFirstPartSumAmount(3500l);
        testSettingsHolder.setSettingsId(0l);
        testSettingsHolder.setUserId(testUser.getUserId());
        testSettingsHolder.setUser(testUser);
        session.save(testSettingsHolder);

        addTestClientSession(testUser, System.currentTimeMillis() - 50000, 0, ClientSession.SESSION_STATUS.CREATED, 0l, session);
        addTestClientSession(testUser, System.currentTimeMillis() - 150000, System.currentTimeMillis(), ClientSession.SESSION_STATUS.REMOVED, Long.valueOf("3508"), session);
        addTestClientSession(testUser, System.currentTimeMillis() - 150000, System.currentTimeMillis(), ClientSession.SESSION_STATUS.PAYED, Long.valueOf("3637"), session);
        Date yesterday = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(yesterday);
        c.add(Calendar.DATE, -1);
        addTestClientSession(testUser, yesterday.getTime(), System.currentTimeMillis(), ClientSession.SESSION_STATUS.PAYED, Long.valueOf("5555"), session);
        addTestClientSession(testUser, System.currentTimeMillis(), System.currentTimeMillis(), ClientSession.SESSION_STATUS.REMOVED, 0l, session);
    }

    @Override
    public List<ClientSession> startClientSession(DatePoint datePoint, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ClientSession clientSessionFromDb = (ClientSession) session.get(clientSession.getClass(), clientSession.getId());
            clientSessionFromDb.setStatus(ClientSession.SESSION_STATUS.STARTED);
            clientSessionFromDb.setStartTime(clientSession.getStartTime());
            transaction.commit();
            return getClientSessionsList(datePoint, UserUtils.INSTANCE.getCurrentUser(), toShowRemoved, toShowPayed);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<ClientSession> unlimClientSession(DatePoint currentDatePointValue, ClientSession clientSession, boolean toShowRemoved, boolean toShowPayed) {
        Session session = HibernateAnnotationUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ClientSession clientSessionFromDb = (ClientSession) session.get(clientSession.getClass(), clientSession.getId());
            clientSessionFromDb.setStatus(ClientSession.SESSION_STATUS.STOPPED_UNLIMITED);
            clientSessionFromDb.setStopTime(clientSession.getStopTime());
            clientSessionFromDb.setFinalSum(clientSession.getFinalSum());
            transaction.commit();
            return getClientSessionsList(currentDatePointValue, UserUtils.INSTANCE.getCurrentUser(), toShowRemoved, toShowPayed);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

//    private long getMaxId() {
//        long maxId = 0;
//        for (Long key : clientSessionMap.keySet()) {
//            if (key > maxId) {
//                maxId = key;
//            }
//        }
//        return maxId;
//    }
//    @Override
//    public void saveClientSession(ClientSession clientSession) {
//        ObjectifyService.ofy().save().entity(clientSession);//To change body of implemented methods use File | Settings | File Templates.
//    }
}
