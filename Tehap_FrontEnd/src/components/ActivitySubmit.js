import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useSelector } from 'react-redux';
import ProfileImageWithDefault from './ProfileImageWithDefault';
import { createActivity, postActivityAttachment } from '../api/apiCalls';
import { useApiProgress } from '../shared/ApiProgress';
import ButtonWithProgress from '../components/ButtonWithProgress';
import Input from '../components/Input';
import AutoUploadImage from '../components/AutoUploadImage';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";


const ActivitySubmit = () => {
    const { image } = useSelector((store) => ({ image: store.image }));
    const [focused, setFocused] = useState(false);

    const { t } = useTranslation();
    const [errors, setErrors] = useState({});
    const [newImage, setNewImage] = useState();
    const [attachmentId, setAttachmentId] = useState();

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [location, setLocation] = useState('');
    const [startDate, setStartDate] = useState(new Date());
    const [activityHour, setActivityHour] = useState('');
    const [activityMinute, setActivityMinute] = useState('');

    useEffect(() => {
        if (!focused) {
            setTitle('');
            setDescription('');
            setLocation('');
            setStartDate('');
            setActivityMinute('');
            setActivityHour('');
            setErrors({});
            setNewImage();
            setAttachmentId();
        }
    }, [focused]);

    const pendingApiCall = useApiProgress('post', '/api/createActivity', true);
    const pendingFileUpload = useApiProgress('post', "/api/activity-attachments", true);


    useEffect(() => {
        setErrors({})
    }, [title]);

    const onChangeFile = (event) => {
        if (event.target.files.length < 1) {
            return;
        }
        const file = event.target.files[0];
        const fileReader = new FileReader();
        fileReader.onloadend = () => {
            setNewImage(fileReader.result);
            uploadFile(file);
        }
        fileReader.readAsDataURL(file);
    };

    const uploadFile = async (file) => {
        const attachment = new FormData();
        attachment.append('image', file)
        const response = await postActivityAttachment(attachment);
        setAttachmentId(response.data.id);
    }

    const onClickEvent = async () => {
        const body = {
            title: title,
            attachmentId: attachmentId,

            description: description,
            location: location,
            startDate: startDate,
            activityHour: activityHour,
            activityMinute: activityMinute
        };

        try {
            console.log(body);
            await createActivity(body);
            setFocused(false);
        } catch (error) {
            if (error.response.data.validationErrors) {
                setErrors(error.response.data.validationErrors)
            }
        }
    }

    let textAreaClass = 'form-control';
    if (errors.title) {
        textAreaClass += ' is-invalid';
    }
    return (
        <div className="card p-1 flex-row">
            <ProfileImageWithDefault image={image} width="32" height="32" className="rounded-circle" />
            <div className="flex-fill">
                {focused && (
                    <>
                        <h6>{t('Enter the Title')}</h6>
                    </>
                )}
                <textarea className={textAreaClass}
                    rows={focused ? '1' : '1'}
                    onFocus={() => setFocused(true)}
                    onChange={(event) => setTitle(event.target.value)}
                    value={title}
                />
                <div className="invalid-feedback">{errors.title}</div>
                {focused && (
                    <>
                        <h6>{t('Enter the Description')}</h6>
                        <textarea className={textAreaClass}
                            onChange={(event) => setDescription(event.target.value)}
                            value={description}
                        />
                        <div className="row">
                            <div className="col-lg-7">
                                <h6>{t('When is the event?')}</h6>
                                <DatePicker selected={startDate} locale="es" onChange={date => setStartDate(date)} />
                            </div>
                            <div className="col-lg-5 d-flex align-items-center">
                                <Input label={t('Hour')} onChange={event => setActivityHour(event.target.value)}></Input> <Input label={t('Minute')} onChange={event => setActivityMinute(event.target.value)}></Input>
                            </div>

                        </div>
                        <Input label={t('Activity Location')} onChange={event => setLocation(event.target.value)}></Input>
                        <h6>{t('Promotional Picture')}</h6>
                        {!newImage && <Input type="file" onChange={onChangeFile} />}
                        {newImage && <AutoUploadImage image={newImage} uploading={pendingFileUpload} />}
                        <div className="text-right mt-1">


                            <ButtonWithProgress className="btn btn-primary"
                                onClick={onClickEvent}
                                text="Activity"
                                pendingApiCall={pendingApiCall}
                                disabled={pendingApiCall || pendingFileUpload}
                            />
                            <button
                                className="btn btn-light d-inline-flex ml-1"
                                onClick={() => setFocused(false)}
                                disabled={pendingApiCall || pendingFileUpload}

                            >
                                <i className="material-icons">close</i>
                                {t('Cancel')}
                            </button>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
};

export default ActivitySubmit;