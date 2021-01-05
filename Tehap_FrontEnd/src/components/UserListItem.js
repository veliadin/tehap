import React from 'react';
import { Link } from 'react-router-dom';
import ProfileImageWithDefault from './ProfileImageWithDefault'

const UserListItem = (props) => {
    const { user } = props;//altgr + , = backtik
    const {username, displayName, image} = user;

    return (
        <div>
            <Link to={`/user/${username}`} className="list-group-item list-group-item-action">
                <ProfileImageWithDefault 
                className="rounded-circle"
                    width="32"
                    height="32"
                    alt={`${username} profile`} image={image} />
                <span className="pl-2"> {username} </span>
                <span className="pl-1"> {displayName} </span>

            </Link>
        </div>
    );
};

export default UserListItem;