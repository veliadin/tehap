import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getActivities, getOldActivities, getNewActivityCount, getNewActivities } from '../api/apiCalls';
import { useApiProgress } from '../shared/ApiProgress';
import ActivityView from './ActivityView';
import Spinner from '../components/Spinner';
import { useParams } from 'react-router-dom';

const Activities = () => {

    const [activityPage, setActivityPage] = useState({ content: [], last: true, number: 0 });
    const [newActivityCount, setNewActivityCount] = useState(0);
    const { t } = useTranslation();
    const { username } = useParams();


    const path = username ? `/api/users/${username}/activities?page=` : `/api/getActivities?page=`;
    const initialActivityLoadProgress = useApiProgress('get', path);

    let lastActivityId = 0;
    let firstActivityId = 0;
    if (activityPage.content.length > 0) {
        firstActivityId = activityPage.content[0].id;
        const lastActivityIndex = activityPage.content.length - 1;
        lastActivityId = activityPage.content[lastActivityIndex].id;

    }
    const oldActivityPath = username ? `/api/users/${username}/activities/${lastActivityId}` : `/api/getActivities/${lastActivityId}`;
    const loadOldActivitiesProgress = useApiProgress('get', oldActivityPath, true)

    const newActivitiesPath = username ? `/api/users/${username}/activities/${firstActivityId}?direction=after` : `/api/getActivities/${firstActivityId}?direction=after`

    const loadNewActivitiesProgress = useApiProgress('get', newActivitiesPath, true)


    useEffect(() => {
        const getCount = async () => {
            const response = await getNewActivityCount(firstActivityId, username);
            setNewActivityCount(response.data.count);
        }
        let looper = setInterval(getCount, 1000);
        ;
        return function cleanup() {
            clearInterval(looper);
        }
    }, [firstActivityId, username])


    useEffect(() => {
        const loadActivities = async page => {
            try {
                const response = await getActivities(username, page);
                setActivityPage(previousActivityPage => ({
                    ...response.data,
                    content: [...previousActivityPage.content, ...response.data.content]
                }))
            } catch (error) { }
        };
        loadActivities();
    }, [username]);


    const loadOldActivities = async () => {

        const response = await getOldActivities(lastActivityId, username);
        setActivityPage(previousActivityPage => ({
            ...response.data,
            content: [...previousActivityPage.content, ...response.data.content]
        }))
    }

    const loadNewActivities = async () => {
        const response = await getNewActivities(firstActivityId, username);
        setActivityPage(previousActivityPage => ({
            ...previousActivityPage,
            content: [...response.data, ...previousActivityPage.content]
        }));
        setNewActivityCount(0);
    }

    const onDeleteActivitySuccess = (id) => {
        setActivityPage(previousActivityPage => ({
            ...previousActivityPage,
            content: previousActivityPage.content.filter((activity) => {
                if (activity.id !== id) {
                    return true
                }
                return false;
            })
        }))
    }

    const { content, last } = activityPage;

    if (content.length === 0) {
        return <div className="alert alert-secondary text-center">
            {initialActivityLoadProgress ? <Spinner /> : t('There are no activities')}
        </div>
    }

    return (
        <div>
            {newActivityCount > 0 && (
                <div className="alert alert-secondary text-center"
                    style={{ cursor: loadNewActivitiesProgress ? 'not-allowed' : 'pointer' }}
                    onClick={loadNewActivitiesProgress ? () => { } : () => loadNewActivities()}
                >
                    {loadNewActivitiesProgress ? <Spinner /> : t('There are new activities')}
                </div>
            )}
            {content.map(activity => {
                return <ActivityView key={activity.id} activity={activity} onDeleteActivity={onDeleteActivitySuccess}></ActivityView>
            })}
            {!last &&
                <div
                    className="alert alert-secondary text-center"
                    style={{ cursor: loadOldActivitiesProgress ? 'not-allowed' : 'pointer' }}
                    onClick={loadOldActivitiesProgress ? () => { } : () => loadOldActivities()}
                >
                    {loadOldActivitiesProgress ? <Spinner /> : t('Load new activities')}
                </div>}
        </div>
    );
};

export default Activities;