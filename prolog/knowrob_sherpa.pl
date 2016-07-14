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
   get_object_size/2,
   get_size/2,
   get_object_properties/2,
   check_object_property/3,
   check_objects_relation/4,
   get_all_salient_objects/1,
   get_all_accommodations/1,
   get_accommodation/1,
   command_to_robot/2,
   send_pose/2,
   check_action/1,
   check_scan_completed/1,
   scan_region/2,
   normal_command/4,
   get_action/2,
   get_preposition/2,
   get_entity/2
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
    sherpa_interface2(r),
    get_all_objects(r),
    get_all_properties(r),
    get_all_accommodations(r),
    get_accommodation(r),
    get_object_size(r,r),
    get_size(r,r),
    get_object_properties(r,r),
    check_object_property(r,r,r),
    check_objects_relation(r,r,r,r),
    get_all_salient_objects(r),
    command_to_robot(r,r),
    send_pose(r,r),
    check_action(r),
    check_scan_completed(r),
    scan_region(r,r),
    normal_command(r,r,r,r),
    get_action(r,r),
    get_preposition(r,r),
    get_entity(r,r).
    
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
    jpl_array_to_list(Ant,Bnt),!.

get_object_properties(Ant,Cnt) :-
    sherpa_interface(SAR),
    jpl_call(SAR, 'addNamespace', [Ant], Bnt),
    map_object_type(Bnt,TYPE),
    owl_has(Bnt, 'http://knowrob.org/kb/knowrob.owl#hasColor',literal(type(_,COLOR))),
    owl_has(Bnt, 'http://knowrob.org/kb/knowrob.owl#isAlive',literal(type(_,MATE))), 
    get_size(Ant,SIZE),
    owl_has(TYPE,rdfs:subClassOf,ANI),
    jpl_call(SAR, 'removeNamespace', [ANI], LIFE),
    jpl_call(SAR, 'elemsToList', [LIFE,MATE,COLOR,SIZE], Dnt),
    jpl_array_to_list(Dnt,Cnt),!.

    get_all_properties(Bnt) :-
    sherpa_interface(SAR),
    jpl_call(SAR, 'getAllProperties', [], Ant),
    jpl_array_to_list(Ant,Bnt),!.

check_object_property(NAME,PROP,Cnt) :-
    sherpa_interface(SAR),
    jpl_call(SAR, 'addNamespace', [NAME], NOM),
    map_object_type(NOM, Ant),
    owl_has(Ant,rdfs:subClassOf,TYPE),
    jpl_call(SAR, 'removeNamespace', [TYPE], TYP),
    jpl_call(SAR, 'setLower', [TYP], TYPO),
    owl_has(NOM, 'http://knowrob.org/kb/knowrob.owl#hasColor',literal(type(_,COLOR))),
    owl_has(NOM, 'http://knowrob.org/kb/knowrob.owl#isAlive',literal(type(_,ALIVE))), 
    get_object_size(NAME, PRO),
    check_rules(TYPO,COLOR,ALIVE,PRO,PROP,Cnt),!.

    check_rules(TYP,COLOR,ALIVE,PRO,PROP,RESULT) :-
    ==(ALIVE,PROP) -> =(RESULT, 'true');
    ==(TYP,PROP) -> =(RESULT, 'true');
    ==(COLOR,PROP) -> =(RESULT, 'true');
    ==(PRO,PROP) -> =(RESULT, 'true');
   =(RESULT,'false'),!.

check_objects_relation(Ant,Bnt,Cnt,Res) :-
    sherpa_interface(SAR),
    jpl_list_to_array(['com.github.knowrob_sherpa.client.SHERPA'], Arr),
    jpl_call('org.knowrob.utils.ros.RosUtilities',runRosjavaNode,[SAR, Arr],_),
    jpl_call(SAR, 'checkRelationBetweenObjects',[Ant,Bnt,Cnt],Ent),
    ==(Ent,1) -> =(Res, 'true');
=(Res,'false'),!.

get_all_salient_objects(Ant) :-
    jpl_new('com.github.knowrob_sherpa.ClientInterface', [], Client),
    jpl_list_to_array(['com.github.knowrob_sherpa.client.SHERPA'], Arr),
    jpl_call('org.knowrob.utils.ros.RosUtilities',runRosjavaNode,[Client, Arr],_),
    jpl_call(Client, 'getAllSalientObjects',[], Bnt),
    jpl_array_to_list(Bnt,Ant),!.
    
get_object_size(Ant,Bnt) :-
    jpl_new('com.github.knowrob_sherpa.ObjSizeInterface', [], Client),
    jpl_list_to_array(['com.github.knowrob_sherpa.client.SHERPA'], Arr),
    jpl_call('org.knowrob.utils.ros.RosUtilities',runRosjavaNode,[Client, Arr],_),
    jpl_call(Client, 'getObjectSize',[Ant],Bnt),!.

get_size(Ant,Bnt) :-
    get_object_size(Ant,Bnt),!.
    
command_to_robot(Ant,S) :-
    get_entity(Ant,ET),
    ==(ET, 'area') -> scan_region(ET,S),!.

command_to_robot(Ant,S) :-
    get_action(Ant,AT),
    get_preposition(Ant,PT),
    get_entity(Ant,ET),
    not(==(ET, 'area')) ->
    normal_command(AT,PT,ET,S),!.
    
get_action(A,S):- 
    sherpa_interface(SAR),
    jpl_call(SAR, 'getAction', [A], S),!.

get_preposition(A,S):- 
    sherpa_interface(SAR),
    jpl_call(SAR, 'getPreposition', [A], S),!.

get_entity(A,S):- 
    sherpa_interface(SAR),
    jpl_call(SAR, 'getEntity', [A], S),!.

normal_command(A,P,E,S):-
   jpl_new('com.github.knowrob_sherpa.LispActionInterface', [], Client),
    jpl_list_to_array(['com.github.knowrob_sherpa.client.Quadrotor'], Arr),
    jpl_call('org.knowrob.utils.ros.RosUtilities',runRosjavaNode,[Client, Arr],_),
    jpl_call(Client, 'sendPose',[A,P,E], ZET),
    send_pose(ZET, S).

send_pose(Zet, Ant) :-
     jpl_new('com.github.knowrob_sherpa.QuadrotorPointsInterface', [], Client),
     jpl_list_to_array(['com.github.knowrob_sherpa.client.Quadrotor'], Arr),
     jpl_call('org.knowrob.utils.ros.RosUtilities',runRosjavaNode,[Client, Arr],_),
     jpl_call(Client, 'getPose',[Zet],_),
     jpl_call(Client, 'sendPose',[], Ant),!.
    
   
    
get_all_accommodations(Ant) :-
    setof(Obj, get_accommodation(Obj), Ant).

get_accommodation(Ant):-
     sherpa_interface(SAR),
     owl_has(Bnt, 'http://knowrob.org/kb/knowrob.owl#isLiving',literal(type(_,_))),    
    jpl_call(SAR, 'removeNamespace', [Bnt], Ant).
 
check_action(Ant) :-
    jpl_new('com.github.knowrob_sherpa.QuadrotorPoseInterface', [], Client),
    jpl_list_to_array(['com.github.knowrob_sherpa.client.Quadrotor'], Arr),
    jpl_call('org.knowrob.utils.ros.RosUtilities',runRosjavaNode,[Client, Arr],_),
    jpl_call(Client, 'getResult',[], Ant),!.

scan_region(A,B):-
    jpl_new('com.github.knowrob_sherpa.QuadrotorScanInterface', [], Client),
    jpl_list_to_array(['com.github.knowrob_sherpa.scanclient.Quadrotor'], Arr),
    jpl_call('org.knowrob.utils.ros.RosUtilities',runRosjavaNode,[Client, Arr],_),
    jpl_call(Client, 'scanArea',[A], B),!.

check_scan_completed(A):-
    jpl_new('com.github.knowrob_sherpa.QuadrotorScanInterface', [], Client),
    jpl_list_to_array(['com.github.knowrob_sherpa.scanclient.Quadrotor'], Arr),
    jpl_call('org.knowrob.utils.ros.RosUtilities',runRosjavaNode,[Client, Arr],_),
    jpl_call(Client, 'checkResult',[], A),!.

