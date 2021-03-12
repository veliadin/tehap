import React from 'react';

const AlertModal = (props) => {
    const { title, message } = props;


    return (

        <div role="alert" style={{ backgroundColor: '#000000b0' }} >
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">{title}</h5>
                    </div>
                    <div className="modal-body">
                        {message}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AlertModal;