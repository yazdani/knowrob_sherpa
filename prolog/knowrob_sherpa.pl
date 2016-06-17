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
   get_all_properties/1,
   get_object_properties/2,
   check_object_property/3,
   check_objects_relation/4
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
    get_all_properties(r),
    get_object_properties(r,r),
    check_object_property(r,r,r),
    check_objects_relation(r,r,r,r).

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
    jpl_call(SAR, 'getAllObjects', [ARR], Ant),
    jpl_array_to_list(Ant,Bnt).

get_object_properties(Ant,Cnt) :-
    sherpa_interface(SAR),
    jpl_call(SAR, 'addNamespace', [Ant], Bnt),
    map_object_type(Bnt,TYPE),
    owl_has(Bnt, 'http://knowrob.org/kb/knowrob.owl#colorOfObject',literal(type(_,COLOR))),
    jpl_call(SAR, 'elemsToList', [TYPE,COLOR], Dnt),
    jpl_array_to_list(Dnt,Cnt).

get_all_properties(Bnt) :-
    sherpa_interface(SAR),
    jpl_call(SAR, 'getAllProperties', [], Ant),
    jpl_array_to_list(Ant,Bnt).

check_object_property(NAME,PROP,Cnt) :-
    sherpa_interface(SAR),
    jpl_call(SAR, 'addNamespace', [NAME], NOM),
    map_object_type(NOM,TYPE),
    jpl_call(SAR, 'replaceString', [TYPE], TYP),
    jpl_call(SAR, 'addNamespace', [PROP], PRO),
    owl_has(NOM, 'http://knowrob.org/kb/knowrob.owl#colorOfObject',literal(type(_,COLOR))), 
    jpl_call(SAR, 'replaceString', [COLOR], FARB),
    check_rules(TYP,FARB,PRO,Cnt).

    check_rules(TYPE,COLOR,PROP,RESULT) :-
    ==(TYPE,PROP) -> =(RESULT, 'true');
    ==(COLOR,PROP) -> =(RESULT, 'true');
    =(RESULT,'false').

check_objects_relation(Ant,Bnt,Cnt,Res) :-
    sherpa_interface(SAR),
    jpl_list_to_array(['com.github.knowrob_sherpa.client.SHERPA'], Arr),
    jpl_call('org.knowrob.utils.ros.RosUtilities',runRosjavaNode,[SAR, Arr],_),
    jpl_call(SAR, 'checkRelationBetweenObjects',[Ant,Bnt,Cnt],Ent),
    ==(Ent,1) -> =(Res, 'true');
=(Res,'false'),!.

