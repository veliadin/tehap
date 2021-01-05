import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import ProfileImageWithDefault from './ProfileImageWithDefault';
import { useTranslation } from "react-i18next";
import Input from "../components/Input";
import { deleteUser, updateUser } from "../api/apiCalls";
import { useApiProgress } from "../shared/ApiProgress";
import ButtonWithProgress from '../components/ButtonWithProgress';
import { logoutSucces, updateSuccess } from '../redux/authActions'
import Activities from "./Activities";
import Modal from './Modal';


const ProfileCard = (props) => {
  const { username: loggedInUsername } = useSelector((store) => {
    return {
      username: store.username,
    };
  });
  const [inEditMode, setInEditMode] = useState(false);
  const [updatedDisplayName, setUpdatedDisplayName] = useState();
  const [user, setUser] = useState({});
  const [editable, setEditable] = useState(false);
  const [newImage, setNewImage] = useState();
  const [validationErrors, setValidationErrors] = useState({});
  const [modalVisible, setModalVisible] = useState(false);
  const history = useHistory();

  const dispatch = useDispatch();

  //const { user } = props;
  const routeParams = useParams();
  const pathUsername = routeParams.username;

  useEffect(() => {
    setUser(props.user);
  }, [props.user]);

  useEffect(() => {
    setEditable(pathUsername === loggedInUsername)
  }, [pathUsername, loggedInUsername])

  const { username, displayName, image, branch, usersurname, email, university } = user;

  const pendingApiCallDeleteUser = useApiProgress('delete', `/api/users/delete/${username}`, true);


  const { t } = useTranslation();

  useEffect(() => {
    if (!inEditMode) {
      setUpdatedDisplayName(undefined);
      setNewImage(undefined);
    } else {
      setUpdatedDisplayName(displayName);
    }
  }, [inEditMode, displayName]);

  useEffect(() => {
    setValidationErrors((previousValidationErrors) => {
      return {
        ...previousValidationErrors,
        displayName: undefined
      }
    });
  }, [updatedDisplayName])

  useEffect(() => {
    setValidationErrors((previousValidationErrors) => {
      return {
        ...previousValidationErrors,
        image: undefined
      }
    });
  }, [newImage])

  const onClickEdit = async () => {
    let image;
    if (newImage) {
      image = newImage.split(',')[1];
    }
    const body = {
      displayName: updatedDisplayName,
      image
    };
    try {
      const response = await updateUser(username, body);
      setInEditMode(false);
      setUser(response.data)
      dispatch(updateSuccess(response.data));
    } catch (error) {
      setValidationErrors(error.response.data.validationErrors);
    }
  }

  const onChangeFile = (event) => {
    if (event.target.files.length < 1) {
      return;
    }
    const file = event.target.files[0];
    const fileReader = new FileReader();
    fileReader.onloadend = () => {
      setNewImage(fileReader.result);
    }
    fileReader.readAsDataURL(file);
  }

  const onClickCancel = () => {
    setModalVisible(false);
  };

  const onClickDeleteUser = async () => {
    await deleteUser(username);
    setModalVisible(false);
    dispatch(logoutSucces());
    history.push('/');
  }


  let message = "We cannot edit";
  if (pathUsername === loggedInUsername) {
    message = "We can edit";
  }
  const pendingApiCall = useApiProgress('put', '/api/users/' + username);

  const { displayName: displayNameError, image: imageError } = validationErrors;
  return (

    <div className="container">
      <br></br>
      <div className="main-body">
        <div className="row gutters-sm">
          <div className="col-md-4 mb-3">
            <div className="card">
              <div className="card-body">
                <div className="d-flex flex-column align-items-center text-center">
                  <ProfileImageWithDefault
                    className="rounded-circle shadow"
                    width="150"
                    height="160"
                    alt={`${username} profile`}
                    image={image}
                    temimage={newImage}
                  />



                  <div className="mt-3">
                    {(!inEditMode &&
                      <h4>{displayName}</h4>)}

                    {editable && (
                      <>
                        <button
                          className="btn btn-success  d-inline-flex"
                          onClick={() => setInEditMode(true)}>
                          <span className="material-icons">edit</span>
                          {t('Edit')}
                        </button>
                        <div className="pt-2">
                          <button
                            className="btn btn-danger d-inline-flex"
                            onClick={() => setModalVisible(true)}>
                            <span className="material-icons">person_remove</span>
                            {t('Delete My Account')}
                          </button>
                        </div>

                      </>
                    )}


                    {(inEditMode && (
                      <div>
                        <Input
                          label={t('Change Display Name')}
                          defaultValue={displayName}
                          onChange={(event) => {
                            setUpdatedDisplayName(event.target.value)
                          }}
                          error={displayNameError}
                        />
                        <Input type="file" onChange={onChangeFile} error={imageError} />
                        <div>
                          <ButtonWithProgress
                            className="btn btn-primary d-inline-flex"
                            onClick={onClickEdit}
                            disabled={pendingApiCall}
                            pendingApiCall={pendingApiCall}
                            text={
                              <>
                                <i className="material-icons">save</i>
                                {t('Save')}
                              </>
                            }
                          />
                          <button
                            className="btn btn-light d-inline-flex ml-1"
                            onClick={() => setInEditMode(false)}
                            disabled={pendingApiCall}>
                            <i className="material-icons">close</i>
                            {t('Cancel')}
                          </button>
                        </div>
                      </div>
                    ))}
                    <br></br><br></br>
                    <p className="text-secondary mb-1"><strong>{branch}</strong></p>
                    <button className="btn btn-primary">{t('Follow')}</button>
                  </div>
                </div>
              </div>
            </div>

          </div>
          <div className="col-md-8">
            <div className="card mb-3">
              <div className="card-body">
                <div className="row">
                  <div className="col-sm-3">
                    <h6 className="mb-0">{t('Name and Surname')}</h6>
                  </div>
                  <div className="col-sm-9 text-secondary">
                    {username + " " + usersurname}
                  </div>
                </div>
                <hr></hr>
                <div className="row">
                  <div className="col-sm-3">
                    <h6 className="mb-0">{t('Email')}</h6>
                  </div>
                  <div className="col-sm-9 text-secondary">
                    {email}
                  </div>
                </div>
                <hr></hr>
                <div className="row">
                  <div className="col-sm-3">
                    <h6 className="mb-0">{t('University')}</h6>
                  </div>
                  <div className="col-sm-9 text-secondary">
                    {university}
                  </div>
                </div>
                <hr></hr>
                <div className="row">
                  <div className="col-sm-3">
                    <h6 className="mb-0">{t('Branch')}</h6>
                  </div>
                  <div className="col-sm-9 text-secondary">
                    {branch}
                  </div>
                </div>
              </div>
            </div>
            <br></br>
            <div className="col-sm-12 mb-12">
              <h4 className="card-title">{t('Events to Attend')}</h4>
              <div className="card h-100">
                <div className="card-body">
                  <div className="col-12">
                    <Activities />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Modal visible={modalVisible}
        onClickCancel={onClickCancel}
        onClickOk={onClickDeleteUser}
        title={t('Delete My Account')}
        okButtonText={t('Delete My Account')}
        pendingApiCall={pendingApiCallDeleteUser}
        message=
        {
          <>{
            <strong>
              {t('Are you sure to delete your account?')}
            </strong>
          }
          </>
        }
      //pendingApiCall={pendingApiCall}
      />
    </div>


    /*
    <div className="card text-center">
      <div className="card-header">
        <ProfileImageWithDefault
          className="rounded-circle shadow"
          width="150"
          height="160"
          alt={`${username} profile`}
          image={image}
          temimage={newImage}
        />
      </div>
      <div className="card-body">
        {(!inEditMode &&
          <>
            <h4>
              {displayName}
              {username}
            </h4>

            {editable && <button
              className="btn btn-success  d-inline-flex"
              onClick={() => setInEditMode(true)}>
              <span className="material-icons">edit</span>
              {t('Edit')}</button>}
          </>
        )}
        {(inEditMode && (
          <div>
            <Input
              label={t('Change Display Name')}
              defaultValue={displayName}
              onChange={(event) => {
                setUpdatedDisplayName(event.target.value)
              }} />
            <input type="file" onChange={onChangeFile} />
            <div>
              <ButtonWithProgress
                className="btn btn-primary d-inline-flex"
                onClick={onClickEdit}
                disabled={pendingApiCall}
                pendingApiCall={pendingApiCall}
                text={
                  <>
                    <i className="material-icons">save</i>
                    {t('Save')}
                  </>
                }
              />


              <button
                className="btn btn-light d-inline-flex ml-1"
                onClick={() => setInEditMode(false)}
                disabled={pendingApiCall}>
                <i className="material-icons">close</i>
                {t('Cancel')}
              </button>
            </div>
          </div>
        ))}


      </div>

    </div>*/
  );
};

export default ProfileCard;
