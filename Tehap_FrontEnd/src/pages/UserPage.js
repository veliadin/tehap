import React, { useState, useEffect } from "react";
import ProfileCard from "../components/ProfileCard";
import { getUser } from "../api/apiCalls";
import { useTranslation } from "react-i18next";
import { useApiProgress } from '../shared/ApiProgress';
import Spinner from "../components/Spinner";
import { useParams } from "react-router-dom";

const UserPage = (props) => {

  const [user, setUser] = useState({});
  const [notFound, setNotFound] = useState(false);

  const { username } = useParams(); //or useParams();

  const { t } = useTranslation();

  const pendingApiCall = useApiProgress('get', '/api/users/' + username, true);


  useEffect(() => {
    setNotFound(false);
  }, [user]);


  useEffect(() => {
    const loadUser = async () => {
      try {
        const response = await getUser(username);
        setUser(response.data);
        setNotFound(false);
      } catch (error) {
        setNotFound(true);
      }
    };
    loadUser();
  }, [username]);


  if (notFound) {
    return (
      <div className="container">
        <div className="alert alert-danger text-center" role="alert">
          <div>
            <span class="material-icons" style={{ fontSize: "48px" }}>
              error
            </span>
          </div>
          {t("User not found.")}
        </div>
      </div>
    );
  }


  if (pendingApiCall || user.username !== username) {
    return <Spinner />
  }




  return (
    <div className="container">
      <ProfileCard user={user} />
    </div>
  );
};

export default UserPage;
