import React from 'react';
import { useTranslation } from 'react-i18next';
import ButtonWithProgress from '../components/ButtonWithProgress';
const Modal = (props) => {
    const { visible, onClickCancel, message, onClickOk, pendingApiCall, title, okButtonText, btnStyle } = props;
    const { t } = useTranslation();
    let className = 'modal fade';
    if (visible) {
        className += ' show d-block'
    }

    return (
        <div className={className} style={{ backgroundColor: '#000000b0' }} >
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">{title}</h5>
                    </div>
                    <div className="modal-body">
                        {message}
                    </div>
                    <div className="modal-footer">
                        <button type="button"
                            className="btn btn-secondary"
                            onClick={onClickCancel}
                            disabled={pendingApiCall}>
                            {t('Cancel')}
                        </button>

                        <ButtonWithProgress
                            className={btnStyle}
                            onClick={onClickOk}
                            pendingApiCall={pendingApiCall}
                            text={okButtonText}
                            disabled={pendingApiCall}
                        />
                    </div>
                </div>
            </div>
        </div >
    );
};

export default Modal;