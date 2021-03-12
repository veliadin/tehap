import React from 'react';
import ProfileImageWithDefault from '../components/ProfileImageWithDefault';
import { Link } from 'react-router-dom';
import { format } from 'timeago.js'
import { useTranslation } from 'react-i18next';
import { useSelector } from 'react-redux';
import { deleteActivity, saveAttendedUser } from '../api/apiCalls';
import Modal from './Modal';
import { useState } from 'react';
import { useApiProgress } from '../shared/ApiProgress';
import AlertModal from './AlertModal';

const ActivityView = (props) => {
    const loggedInUser = useSelector(store => store.username);
    const { activity, onDeleteActivity } = props;
    const { user, content, timestamp, fileAttachmentViewModel, id } = activity;
    const { username, image } = user;
    const [modalDeleteVisible, setModalDeleteVisible] = useState(false);
    const [modalJoinVisible, setModalJoinVisible] = useState(false);

    const { t } = useTranslation();
    const { i18n } = useTranslation();

    const formatted = format(timestamp, i18n.language);

    const ownedByLoggedInUser = loggedInUser === username;

    const pendingDeleteApiCall = useApiProgress('delete', `/api/deleteActivity/${id}`, true);

    const onClickDeleteActivity = async () => {
        await deleteActivity(id);
        onDeleteActivity(id);
    };
    const onClickCancelDelete = () => {
        setModalDeleteVisible(false);
    };

    const onClickJoinActivity = async () => {
        console.log('Activity ID : ' + id);
        await saveAttendedUser(id);
        setModalJoinVisible(false);

    };
    const onClickCancelJoin = () => {
        setModalJoinVisible(false);
    }
    return (
        <>
            <div className="card mb-3 border-0">
                <div className="row no-gutters d-flex">
                    <div className="col-md-3">
                        {fileAttachmentViewModel && (
                            <div>
                                {fileAttachmentViewModel.fileType.startsWith('image') && (
                                    <img className="img-fluid" style={{ height: 170, width: 300 }} src={'images/attachments/' + fileAttachmentViewModel.name} alt={content} />
                                )}
                            </div>
                        )}
                    </div>
                    <div className="col-md-8">
                        <div className="card-body">
                            <h4 className="card-title"> {activity.title}</h4>
                            <p className="card-text">{activity.description}</p>
                        </div>
                        <div className="col-md-12">
                            <h6>{t('Location')}{" : " + activity.location}</h6>
                        </div>
                        <div className="col-md-12">
                            <h6>{t('Date')}{" : " + activity.startDate}</h6><h6>{t('When')}{" : " + activity.activityHour}{":" + activity.activityMinute}</h6>
                        </div>
                    </div>

                    {ownedByLoggedInUser && (<button
                        className="btn btn-delete-link m-auto"
                        style={{ marginLeft: 50 }}
                        onClick={() => setModalDeleteVisible(true)}
                    >
                        <span className="material-icons">
                            delete_outline
                    </span>
                    </button>)}
                    {!ownedByLoggedInUser && loggedInUser && (<button
                        className="btn btn-delete-link m-auto"
                        style={{ marginLeft: 50 }}
                        onClick={() => setModalJoinVisible(true)}
                    >
                        <span className="material-icons">
                            keyboard_arrow_right
                        </span>
                    </button>)}
                </div>
                <div className="card-footer text-muted d-flex">
                    <ProfileImageWithDefault image={image} width="32" height="32" className="rounded-circle m-1" />
                    <p className="flex-fill m-auto pl-2">
                        {formatted} tarihinde <Link to={`/user/${username}`} className="text-dark">
                            {username}
                        </Link> tarafından oluşturuldu.
                    </p>
                </div>
            </div>
            <Modal
                visible={modalDeleteVisible}
                onClickCancel={onClickCancelDelete}
                onClickOk={onClickDeleteActivity}
                btnStyle="btn btn-danger"
                title={t('Delete Activity')}
                message={
                    <>
                        <p><strong>{t('Are you sure to delete activity?')}</strong></p>
                        {activity.title}
                    </>
                }
                pendingDeleteApiCall={pendingDeleteApiCall}
                okButtonText={t('Delete Activity')}
            />
            <Modal
                visible={modalJoinVisible}
                onClickCancel={onClickCancelJoin}
                onClickOk={onClickJoinActivity}
                className="success"
                title={t('Join Activity')}
                btnStyle="btn btn-success"
                message={
                    <>
                        <p><strong>{t('Are you sure to join this activity?')}</strong></p>
                        {activity.title}
                    </>
                }
                pendingDeleteApiCall={pendingDeleteApiCall}
                okButtonText={t('Join Activity')}
            />
        </>
    );
};

export default ActivityView;