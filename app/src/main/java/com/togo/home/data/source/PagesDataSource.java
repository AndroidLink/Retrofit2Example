/*
 *  Copyright(c) 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.togo.home.data.source;

import com.togo.home.data.retrofit.response.PatientFirstPageModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Main entry point for accessing packages data.
 * <p>
 */

public interface PagesDataSource {

    Observable<List<PatientFirstPageModel>> getFirstPageModels();

    void refreshFirstPageModels();

    void saveFirstPageModel(PatientFirstPageModel pageModel);
}
