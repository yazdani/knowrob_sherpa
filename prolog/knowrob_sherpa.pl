/** knowrob_sherpa
Copyright (C) 2016 Fereshta Yazdani
  All rights reserved.

  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are met:
      * Redistributions of source code must retain the above copyright
        notice, this list of conditions and the following disclaimer.
      * Redistributions in binary form must reproduce the above copyright
        notice, this list of conditions and the following disclaimer in the
        documentation and/or other materials provided with the distribution.
      * Neither the name of the <organization> nor the
        names of its contributors may be used to endorse or promote products
        derived from this software without specific prior written permission.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
  DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

  @author Fereshta Yazdani
  @license BSD
*/

:- module(knowrob_sherpa,
  [
   sherpa_interface/0,
   sherpa_interface/1,
   get_all_objects/1,
   check_object_type/3,
   get_object_type/2
  ]).

:- use_module(library('semweb/rdf_db')).
:- use_module(library('semweb/rdfs')).
:- use_module(library('owl_parser')).
:- use_module(library('owl')).
:- use_module(library('rdfs_computable')).
:- use_module(library('knowrob_owl')).
:- use_module(library('jpl')).

:- rdf_meta sherpa_test(r,r),
    sherpa_interface(r),
    get_all_objects(r),
    check_object_type(r,r,r),
    get_object_type(r,r).

:- rdf_db:rdf_register_ns(knowrob, 'http://knowrob.org/kb/knowrob.owl#', [keep(true)]).

sherpa_interface :- sherpa_interface(_).

:-assert(sherpa_inter(fail)).
sherpa_interface(SAR) :-
    sherpa_inter(fail),
    jpl_new('com.github.knowrob_sherpa.SARInterface', [], SAR),
    retract(sherpa_inter(fail)),
    assert(sherpa_inter(SAR)),!.

sherpa_interface(DB) :-
    sherpa_inter(DB).

get_all_objects(Bnt) :-
    sherpa_interface(SAR),
    map_root_objects(_,ALL),
    jpl_list_to_array(ALL, ARR),
    jpl_call(SAR, 'queryAllObjects', [ARR], Ant),
    jpl_array_to_list(Ant,Bnt).

get_object_type(Ant,Bnt) :-
    sherpa_interface(SAR),
    jpl_call(SAR, 'bindNamespace', [Ant], NAM),
    map_object_type(NAM,Cnt),
    jpl_call(SAR, 'getObjectType',[Cnt], Bnt).

check_object_type(Ant,Bnt,Cnt) :-
    sherpa_interface(SAR),
    jpl_call(SAR, 'bindNamespace', [Ant], NAM),
    map_object_type(NAM,TYP),
    jpl_call(SAR, 'checkObjectType',[TYP,Bnt], Cnt).
